package com.ssafit.member.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 DTO")
public class MemberSignupRequestDto {
	
	@Schema(description = "회원 아이디", example = "ssafy1234")
	private String userId;
	@Schema(description = "회원 비밀번호", example = "Ssafy1234!")
	private String userPw;
	@Schema(description = "회원 이름", example = "양싸피")
	private String name;
	@Schema(description = "회원 닉네임", example = "띵균좌")
	private String nickname;
	@Schema(description = "회원 이메일", example = "ssafy@ssafy.com")
	private String email;
	@Schema(description = "생년월일", example = "2001-01-01")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate birth;
	@Schema(description = "선호하는 운동부위", example = "[\"등\", \"어깨\"]")
	private List<Integer> category;
	
	public MemberSignupRequestDto() { }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public List<Integer> getCategory() {
		return category;
	}

	public void setCategory(List<Integer> category) {
		this.category = category;
	}

}
