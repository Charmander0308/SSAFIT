package com.ssafit.comment.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description="댓글 응답 dto")
public class CommentResponseDto {
	@Schema(description="댓글 id", example="1")
	private Long id;
	@Schema(description="댓글 내용", example="안녕하세요")
	private String content;
	@Schema(description="작성 일자", example="2025-02-12T14:30:00")
	private LocalDateTime uploadDate;
	@Schema(description="게시글 id. 유튜브 비디오 id와 양립불가", example="1")
	private Long communityId;
	@Schema(description="유튜브 비디오 id. 게시글 id와 양립불가", example="1")
	private Long youtubeVideoId;
	@Schema(description="댓글 작성자 id(PK)", example="3")
	private Long memberId;
	@Schema(description="댓글 작성자 닉네임", example="근육맨")
	private String nickname;
	@Schema(description="댓글 작성자 프로필사진 경로", example="/img/1.jpg")
	private String profileImg;
	
	public CommentResponseDto() {}

	public CommentResponseDto(Long id, String content, LocalDateTime uploadDate, Long communityId, Long youtubeVideoId,
			Long memberId, String nickname, String profileImg) {
		super();
		this.id = id;
		this.content = content;
		this.uploadDate = uploadDate;
		this.communityId = communityId;
		this.youtubeVideoId = youtubeVideoId;
		this.memberId = memberId;
		this.nickname = nickname;
		this.profileImg = profileImg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	@Override
	public String toString() {
		return "CommentResponseDto [id=" + id + ", content=" + content + ", uploadDate=" + uploadDate + ", communityId="
				+ communityId + ", youtubeVideoId=" + youtubeVideoId + ", memberId=" + memberId + ", nickname="
				+ nickname + ", profileImg=" + profileImg + "]";
	}


}
