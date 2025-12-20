package com.ssafit.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.comment.dto.CommentRequestDto;
import com.ssafit.comment.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "댓글 API", description = "댓글 작성 API")
@RestController
@RequestMapping("/comment")
public class CommentController {
	private final CommentService commentService;
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@Operation(summary="댓글 작성", description="사용자 id를 추출한 뒤 전달받은 댓글요청Dto에 저장하여 댓글을 등록합니다.")
	@PostMapping
	public ResponseEntity<Void> commentWrite(@RequestBody CommentRequestDto commentRequestDto,
			@Parameter(hidden = true) @AuthenticationPrincipal (expression = "id") Long memberId) {
		commentRequestDto.setMemberId(memberId);
		commentService.writeComment(commentRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
