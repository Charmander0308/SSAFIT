package com.ssafit.challenge.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.challenge.dto.ChallengeResponseDto;
import com.ssafit.challenge.dto.ChallengeUserDto;
import com.ssafit.challenge.service.ChallengeService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "업적 API", description = "업적 조회,작성 API")
@RestController
@RequestMapping("/challenge")
public class ChallengeController {
	private final ChallengeService challengeService;
	public ChallengeController(ChallengeService challengeService) {
		this.challengeService = challengeService;
	}
	
	@Operation(summary="업적 조회", description="업적 전체 리스트를 반환(달성여부 상관없음)")
	@GetMapping("/board")  
	public ResponseEntity<List<ChallengeResponseDto>> getChallenge(
			@Parameter(hidden = true) @AuthenticationPrincipal (expression = "id") Long memberId) {
		return ResponseEntity.ok(challengeService.getChallengeList(memberId));
	}
	
//	@Operation(summary="유저의 업적 달성 현황 조회", description="달성한 업적 리스트/진행 중인 업적 리스트 두 가지를 반환")
//	@GetMapping("/rate")
//	public ResponseEntity<ChallengeUserDto> challengeInfo(
//			@Parameter(hidden = true) @AuthenticationPrincipal (expression = "id") Long memberId){
//		return ResponseEntity.ok(challengeService.userChallengeInfo(memberId));
//	}
	
	@Operation(summary="유저의 업적 달성 현황 조회", description="달성한 업적 리스트/진행 중인 업적 리스트 두 가지를 반환")
	@GetMapping("/rate/{id}")
	public ResponseEntity<ChallengeUserDto> challengeInfo(
			@PathVariable("id") Long memberId){
		return ResponseEntity.ok(challengeService.userChallengeInfo(memberId));
	}
	
//	@Operation(summary="", description="")
//	@GetMapping("/{eventType}") 
//	public void challegeWrite(@PathVariable("eventType") String eventType) {
//		//사용자 id값 추출하여 서비스로 전달
//		Long memberId = (long) 1;
//		challengeService.getChellenge(eventType, memberId);
//	}
//	
//	
}
