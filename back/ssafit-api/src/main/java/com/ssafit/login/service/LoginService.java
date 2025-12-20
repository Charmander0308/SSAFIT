package com.ssafit.login.service;

import java.time.Duration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafit.global.config.jwt.JwtTokenProvider;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;
import com.ssafit.login.dto.LoginResponseDto;
import com.ssafit.member.dao.MemberDao;
import com.ssafit.member.dto.MemberAuthDto;

@Service
@Transactional
public class LoginService implements UserDetailsService {

	private final MemberDao memberDao;
	private final JwtTokenProvider jwtTokenProvider;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public LoginService(MemberDao memberDao, JwtTokenProvider jwtTokenProvider, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.memberDao = memberDao;
		this.jwtTokenProvider = jwtTokenProvider;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
		
		String accessToken = jwtTokenProvider.generateAccessToken(memberAuthDto);
		String refreshToken = jwtTokenProvider.generateRefreshToken(memberAuthDto);
		
		memberDao.updateRefreshToken(memberAuthDto.getId(), refreshToken);
		
		return new LoginResponseDto(accessToken, refreshToken);
	}

}
