package com.ssafit.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.login.dto.LoginRequestDto;
import com.ssafit.login.service.LoginService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "회원 인증 컨트롤러", description = "로그인, 로그아웃, 토큰 재발급 등을 처리하는 API")
public class LoginController {
	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
		return ResponseEntity.ok(loginService.login(loginRequestDto.getId(), loginRequestDto.getPassword()));
	}
}
