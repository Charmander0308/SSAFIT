package com.ssafit.community.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.community.dto.LikedDto;
import com.ssafit.community.service.LikedService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="좋아요 API", description="좋아요 등록/취소 API")
@RestController
@RequestMapping("/liked")
public class LikedController {
	private final LikedService likedService;
	public LikedController(LikedService likedService) {
		this.likedService = likedService;
	}
	
	@Operation(summary="좋아요 등록/취소", 
			description="사용자가 좋아요 버튼 클릭시 좋아요 상태를 전환합니다. 뷰로부터 게시글 id가 담긴 dto객체를 받아 추출한 사용자 id를 주입합니다.")
	@PostMapping
	public ResponseEntity<Void> doLiked(@RequestBody LikedDto likedDto,
			@Parameter(hidden = true) @AuthenticationPrincipal (expression = "id") Long memberId) {
		likedDto.setMemberId(memberId);
		likedService.doLiked(likedDto);
		return ResponseEntity.noContent().build();
	}
}
