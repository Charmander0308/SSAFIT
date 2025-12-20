package com.ssafit.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "팔로우 DTO")
public class FollowDto {
	@Schema(description = "팔로우 요청한 회원 id 값", example = "1")
	private Long fromId;
	@Schema(description = "팔로우를 요청받은 회원 id 값", example = "2")
	private Long toId;
	
	public FollowDto() {
	}
	
	public FollowDto(Long fromId, Long toId) {
		this.fromId = fromId;
		this.toId = toId;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}
	
}
