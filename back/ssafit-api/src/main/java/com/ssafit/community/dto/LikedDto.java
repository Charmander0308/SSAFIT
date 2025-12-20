package com.ssafit.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="좋아요 요청/응답 Dto")
public class LikedDto {
	@Schema(description="게시글 id", example="1")
	private Long communityId;
	@Schema(description="사용자 id", example="1")
	private Long memberId;
	
	public LikedDto() {}
	public LikedDto(Long communityId, Long memberId) {
		super();
		this.communityId = communityId;
		this.memberId = memberId;
	}
	public Long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	@Override
	public String toString() {
		return "LikedDto [communityId=" + communityId + ", memberId=" + memberId + "]";
	}
}
