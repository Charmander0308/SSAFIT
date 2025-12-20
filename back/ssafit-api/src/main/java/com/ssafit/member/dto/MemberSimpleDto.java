package com.ssafit.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 단순정보 DTO")
public class MemberSimpleDto {
	@Schema(description = "회원 id 값", example = "1")
	private Long id;
	@Schema(description = "회원 이름", example = "양싸피")
	private String name;
	@Schema(description = "회원 닉네임", example = "띵균좌")
	private String nickname;
	@Schema(description = "프로필 사진 경로", example = "https://s3.ap-northeast-2.amazonaws.com/ssafit/profile/1.jpg")
	private String profileImgDirectory;

	public MemberSimpleDto() {
	}
	
	public MemberSimpleDto(Long id, String name, String nickname, String profileImgDirectory) {
		this.id = id;
		this.name = name;
		this.nickname = nickname;
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

	public String getProfileImgDirectory() {
		return profileImgDirectory;
	}

	public void setProfileImgDirectory(String profileImgDirectory) {
		this.profileImgDirectory = profileImgDirectory;
	}
	
}
