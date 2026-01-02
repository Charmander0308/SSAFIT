package com.ssafit.member.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 기본정보 DTO")
public class MemberProfileResponseDto {
	@Schema(description = "회원 id 값", example = "1")
	private Long id;
	@Schema(description = "회원 이름", example = "양싸피")
	private String name;
	@Schema(description = "회원 닉네임", example = "띵균좌")
	private String nickname;
	@Schema(description = "생년월일", example = "2001-01-01")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date birth;
	@Schema(description = "가입일", example = "2025-07-21")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date signdate;
	@Schema(description = "회원 이메일", example = "ssafy@ssafy.com")
	private String email;
	@JsonIgnore
	private Integer interestCode;
	@Schema(description = "선호하는 운동부위", example = "[\"등\", \"어깨\"]")
	private List<String> interests;
	@Schema(description = "프로필 사진 경로", example = "https://s3.ap-northeast-2.amazonaws.com/ssafit/profile/1.jpg")
	private String profileImgDirectory;

	public MemberProfileResponseDto() {
	}
	
	public MemberProfileResponseDto(Long id, String name, String nickname, Date birth, Date signdate, String email,
			Integer interestCode, List<String> interests, String profileImgDirectory) {
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.birth = birth;
		this.signdate = signdate;
		this.email = email;
		this.interestCode = interestCode;
		this.interests = interests;
		this.profileImgDirectory = profileImgDirectory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getInterestCode() {
		return interestCode;
	}

	public void setInterestCode(Integer interestCode) {
		this.interestCode = interestCode;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}

	public String getProfileImgDirectory() {
		return profileImgDirectory;
	}

	public void setProfileImgDirectory(String profileImgDirectory) {
		this.profileImgDirectory = profileImgDirectory;
	}
	
}
