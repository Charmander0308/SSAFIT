package com.ssafit.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="게시글 요청 dto")
public class CommentRequestDto {
	@Schema(description="댓글 내용", example="hello")
	private String content;
	@Schema(description="게시글 id. 유튜브 비디오id와 양립불가", example="1")
	private Long communityId;
	@Schema(description="유튜브 비디오id. 게시글 id와 양립불가", example="1")
	private Long youtubeVideoId;
	@Schema(description="사용자 id. 컨트롤러에서 추출", example="1")
	private Long memberId;
	
	public CommentRequestDto() {}

	public CommentRequestDto(String content, Long communityId, Long youtubeVideoId, Long memberId) {
		super();
		this.content = content;
		this.communityId = communityId;
		this.youtubeVideoId = youtubeVideoId;
		this.memberId = memberId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public Long getYoutubeVideoId() {
		return youtubeVideoId;
	}

	public void setYoutubeVideoId(Long youtubeVideoId) {
		this.youtubeVideoId = youtubeVideoId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "CommentRequestDto [content=" + content + ", communityId=" + communityId + ", youtubeVideoId="
				+ youtubeVideoId + ", memberId=" + memberId + "]";
	}
	
}
