package com.ssafit.member.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.member.dto.FollowDto;
import com.ssafit.member.dto.MemberProfileResponseDto;
import com.ssafit.member.dto.MemberSignupRequestDto;
import com.ssafit.member.dto.MemberSimpleDto;
import com.ssafit.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/member")
@Tag(name = "회원 API", description = "회원 관련 API")
public class MemberController {
	
	private MemberService memberService;
	
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/signup")
	@Operation(summary = "회원가입", description = "회원 정보를 받아 회원가입을 한다.")
	public ResponseEntity<Void> regist(@RequestBody MemberSignupRequestDto memberSignupRequestDto){
		memberService.regist(memberSignupRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/mypage")
	@Operation(summary = "마이페이지", description = "로그인 한 회원의 마이페이지로 이동한다.")
	public ResponseEntity<MemberProfileResponseDto> mypage(@Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long id){
		return ResponseEntity.ok(memberService.findProfile(id));
	}
	
	@GetMapping("/{id}/followers")
	@Operation(summary = "팔로워 목록", description = "특정 회원의 팔로워 목록을 전체 조회한다.")
	public ResponseEntity<List<MemberSimpleDto>> getFollowers(@Parameter(description = "조회 대상의 id 값", example = "1") @PathVariable("id") Long id) {
		return ResponseEntity.ok(memberService.findFollower(id));
	}
	
	@GetMapping("/{id}/followings")
	@Operation(summary = "팔로잉 목록", description = "특정 회원의 팔로잉 목록을 전체 조회한다.")
	public ResponseEntity<List<MemberSimpleDto>> getFollowings(@Parameter(description = "조회 대상의 id 값", example = "1") @PathVariable("id") Long id) {
		return ResponseEntity.ok(memberService.findFollowing(id));
	}
	
	@PostMapping("/{id}/follow")
	@Operation(summary = "팔로우 신청", description = "다른 회원을 팔로우한다.")
	public ResponseEntity<Void> follow(
			@Parameter(description = "팔로우 할 상대의 id 값", example = "1") @PathVariable("id") Long toId, 
			@Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long fromId){
		memberService.follow(new FollowDto(fromId, toId));
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/status/{id}")
	public ResponseEntity<Boolean> isFollowing(
			@Parameter(description = "상대의 id 값", example = "1") @PathVariable("id") Long toId,
			@Parameter(hidden = true) @AuthenticationPrincipal(expression = "id") Long fromId){
		return ResponseEntity.ok(memberService.isFollowing(new FollowDto(fromId, toId)));
	}
	
}
