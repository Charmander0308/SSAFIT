package com.ssafit.dashboard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafit.challenge.service.ChallengeService;
import com.ssafit.dashboard.dto.DashboardResponseDto;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;
import com.ssafit.member.dto.MemberStatusResponseDto;
import com.ssafit.member.service.MemberService;

@Service
@Transactional(readOnly = true)
public class DashboardService {
	private final MemberService memberService;
	private final ChallengeService challengeService;
	
	public DashboardService(MemberService memberService, ChallengeService challengeService) {
		this.memberService = memberService;
		this.challengeService = challengeService;
	}
	
	public DashboardResponseDto getDashboardData(Long id) {
		MemberStatusResponseDto memberDto = memberService.findStatus(id);
		if(memberDto == null) {
			throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
		}
		
		List<String> challengesList = challengeService.achievedChallenges(id);
		
		return DashboardResponseDto.builder()
				.memberDto(memberDto)
				.achievedChallenges(challengesList)
				.build();
	}
}
