package com.ssafit.community.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafit.comment.dto.CommentResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 응답 DTO")
public class CommunityResponseDto {
	@Schema(description = "게시글 id 값", example = "1")
    private Long id;	
	@Schema(description = "게시글의 비디오 보유 여부를 나타냄(0:일반게시물/1:비디오게시물)", example = "1")
    private Integer type;  
	@Schema(description = "게시글 제목", example = "운동 같이해요.")
    private String title;
	@Schema(description = "게시글 내용", example = "운동 같이하실분 모집합니다. 매주 금요일 19시 싸피체육관에서 진행합니다.")
    private String content;
	@Schema(description = "게시글 조회 수", example = "1331")
    private Integer views;
	@Schema(description = "최초 등록 일시", example = "2025-11-21 09:21:44")
    private LocalDateTime uploadDate;	
	@Schema(description = "최종 수정 일시", example = "2025-12-12 09:33:14")
    private LocalDateTime updateDate;
	@Schema(description = "업로드 영상 경로", example = "https://s3.ap-northeast-2.amazonaws.com/ssafit/video/hello.mp4")
    private String videoDirectory;
	@Schema(description = "썸네일 사진 경로", example = "https://s3.ap-northeast-2.amazonaws.com/ssafit/thumbnail/hello.jpg")
    private String thumbnailDirectory;
	@Schema(description = "게시글의 카테고리(부위)", example = "4")
    private Integer category;
	@Schema(description = "회원 닉네임", example = "띵균좌")
    private String nickname;	
	@Schema(description = "좋아요 수", example = "112")
    private Integer liked;
	@Schema(description = "좋아요 여부 확인", example = "true")
    private boolean likedCheck;
	@Schema(description = "해당 게시글의 댓글 목록")
    private List<CommentResponseDto> comments;
	@Schema(description = "댓글 수", example = "2")
    private Integer commentCnt;
 

	public CommunityResponseDto() {}

	public CommunityResponseDto(Long id, Integer type, String title, String content, Integer views,
			LocalDateTime uploadDate, LocalDateTime updateDate, String videoDirectory, String thumbnailDirectory,
			Integer category, String nickname, Integer liked, boolean likedCheck, List<CommentResponseDto> comments,
			Integer commentCnt) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.content = content;
		this.views = views;
		this.uploadDate = uploadDate;
		this.updateDate = updateDate;
		this.videoDirectory = videoDirectory;
		this.thumbnailDirectory = thumbnailDirectory;
		this.category = category;
		this.nickname = nickname;
		this.liked = liked;
		this.likedCheck = likedCheck;
		this.comments = comments;
		this.commentCnt = commentCnt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public String getVideoDirectory() {
		return videoDirectory;
	}

	public void setVideoDirectory(String videoDirectory) {
		this.videoDirectory = videoDirectory;
	}

	public String getThumbnailDirectory() {
		return thumbnailDirectory;
	}

	public void setThumbnailDirectory(String thumbnailDirectory) {
		this.thumbnailDirectory = thumbnailDirectory;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getLiked() {
		return liked;
	}

	public void setLiked(Integer liked) {
		this.liked = liked;
	}

	public boolean isLikedCheck() {
		return likedCheck;
	}

	public void setLikedCheck(boolean likedCheck) {
		this.likedCheck = likedCheck;
	}

	public List<CommentResponseDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentResponseDto> comments) {
		this.comments = comments;
	}

	public Integer getCommentCnt() {
		return commentCnt;
	}

	public void setCommentCnt(Integer commentCnt) {
		this.commentCnt = commentCnt;
	}

	@Override
	public String toString() {
		return "CommunityResponseDto [id=" + id + ", type=" + type + ", title=" + title + ", content=" + content
				+ ", views=" + views + ", uploadDate=" + uploadDate + ", updateDate=" + updateDate + ", videoDirectory="
				+ videoDirectory + ", thumbnailDirectory=" + thumbnailDirectory + ", category=" + category
				+ ", nickname=" + nickname + ", liked=" + liked + ", likedCheck=" + likedCheck + ", comments="
				+ comments + ", commentCnt=" + commentCnt + "]";
	}
}
