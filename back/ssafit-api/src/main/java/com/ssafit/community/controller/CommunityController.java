package com.ssafit.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.community.dto.CommunityRequestDto;
import com.ssafit.community.dto.CommunityResponseDto;
import com.ssafit.community.dto.CommunitySearchCondition;
import com.ssafit.community.service.CommunityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "게시글 API", description = "게시글 CRUD API")
@RestController
@RequestMapping("/community")
public class CommunityController {
	private final CommunityService communityService;
	public CommunityController(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	
	@Operation(summary="게시글 목록 조회", description="페이지 조건에 맞는 게시물 목록 반환")
	@GetMapping
	public ResponseEntity<List<CommunityResponseDto>> communityList(@ModelAttribute CommunitySearchCondition condition) {
		return ResponseEntity.ok(communityService.getCommunityList(condition));
	}
	
	
	@Operation(summary="게시글 세부 내용 조회", description="선택한 게시글 정보 및 댓글 조회")
	@GetMapping("/{communityId}")
	public ResponseEntity<CommunityResponseDto> communityDetail(
			@Parameter(description="게시글 id", example="1") @PathVariable("communityId") Long communityId,
			@Parameter(hidden = true) @AuthenticationPrincipal (expression = "id") Long memberId) {
		return ResponseEntity.ok(communityService.readCommunity(communityId, memberId));
	}
	
	
	@Operation(summary="게시글 작성", description="게시글 작성 요청 처리")
	@PostMapping
	public ResponseEntity<Void> communityWrite(@RequestBody CommunityRequestDto communityRequestDto,
			@Parameter(hidden = true) @AuthenticationPrincipal (expression = "id") Long memberId) {
		communityRequestDto.setMemberId(memberId);
		communityService.writeCommunity(communityRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	
	}
}
