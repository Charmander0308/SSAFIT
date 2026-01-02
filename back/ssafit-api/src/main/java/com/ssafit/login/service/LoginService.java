package com.ssafit.login.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafit.challenge.dto.ChallengeEvent;
import com.ssafit.challenge.dto.Domains;
import com.ssafit.global.config.jwt.JwtTokenProvider;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;
import com.ssafit.login.dto.LoginResponseDto;
import com.ssafit.member.dao.MemberDao;
import com.ssafit.member.dto.MemberAuthDto;
import com.ssafit.member.dto.MemberVisitedCheckDto;

@Service
@Transactional
public class LoginService implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(LoginService.class);

	private final MemberDao memberDao;
	private final JwtTokenProvider jwtTokenProvider;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final ApplicationEventPublisher publisher;
	
	public LoginService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider,
			BCryptPasswordEncoder bCryptPasswordEncoder, ApplicationEventPublisher publisher) {
		this.memberDao = memberDao;
		this.jwtTokenProvider = jwtTokenProvider;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.publisher = publisher;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return memberDao.findByUserId(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}
	//실제 로그인 메서드
	public LoginResponseDto login(String userId, String password) {
		MemberAuthDto memberAuthDto = memberDao.findByUserId(userId)
				.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
		
		if(!bCryptPasswordEncoder.matches(password, memberAuthDto.getPassword())) {
			throw new CustomException(ErrorCode.AUTH_FAILED);
		}
		
		//로그인 시 발생하는 이벤트 
		//1. 로그인한 현재 날짜를 last_login_date와 비교해서 다르면 total_visited+1 
		//   하루 차이인지 확인. 맞다면 continuous_visited+1 
		try {
			int result = visitedInfoUpdate(memberAuthDto.getId());
			if(result == 0) {
				log.warn("출석 정보 업데이트 실패 (대상 없음) - userId: {}", userId);
			}
		} catch (Exception e) {
			log.error("출석 정보 업데이트 중 에러 발생 - userId: " + userId, e);
		}
		
		ChallengeEvent event = new ChallengeEvent(Domains.LOGIN.getDomain(), memberAuthDto.getId());
		publisher.publishEvent(event);
		
		String accessToken = jwtTokenProvider.generateAccessToken(memberAuthDto);
		String refreshToken = jwtTokenProvider.generateRefreshToken(memberAuthDto);
		
		memberDao.updateRefreshToken(memberAuthDto.getId(), refreshToken);
		
		return new LoginResponseDto(accessToken, refreshToken);
	}

	//최종 로그인, 총 방문횟수, 연속 방문횟수 갱신
	private int visitedInfoUpdate(Long id) {
		MemberVisitedCheckDto dto = memberDao.findVisitedDataById(id);
		if(dto == null) return 0;
		
		LocalDateTime loginDateTime = LocalDateTime.now();
		LocalDate loginDate = loginDateTime.toLocalDate();
		LocalDateTime lastLogin =  dto.getLastLoginDate();
		
		int totalDay = 1, continuousDay = 1;
		if(lastLogin != null) {
			LocalDate last = lastLogin.toLocalDate();
			if(last.equals(loginDate)) {
				totalDay = dto.getTotalVisited();
				continuousDay = dto.getContinuousVisited();
			}
			else {
				totalDay = dto.getTotalVisited() + 1;
				if(last.isEqual(loginDate.minusDays(1))) {
					continuousDay = dto.getContinuousVisited() + 1;
				}
			}
		}
		dto.setLastLoginDate(loginDateTime);
		dto.setTotalVisited(totalDay);
		dto.setContinuousVisited(continuousDay);

		return memberDao.updateVisitedInfo(dto);
		
	}

}
