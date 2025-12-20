	package com.ssafit.community.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 요청 DTO")
public class CommunityRequestDto {
	@Schema(description = "게시글의 비디오 보유 여부를 나타냄(0:일반게시물/1:비디오게시물)", example="0")
    private Integer type;  
	@Schema(description = "게시글 제목", example="오늘의 운동")
    private String title;
	@Schema(description = "게시글 내용", example="오늘도 30분 전신 운동 완료하였습니다.")
    private String content;
	@Schema(description = "비디오 저장 경로", example="https://s3.ap-northeast-2.amazonaws.com/ssafit/video/hello.mp4")
    private String videoDirectory;
	@Schema(description = "비디오 썸네일 저장 경로", example="https://s3.ap-northeast-2.amazonaws.com/ssafit/thumbnail/hello.jpg")
    private String thumbnailDirectory;
	@Schema(description = "게시글 카테고리(1,2,4,8,16,32,64,128,256)", example="128")
    private Integer category;
	@Schema(description = "게시글 작성자 id", example="1")
    private Long memberId;
    
    CommunityRequestDto(){}

	public CommunityRequestDto(Integer type, String title, String content, String videoDirectory,
			String thumbnailDirectory, Integer category, Long memberId) {
		super();
		this.type = type;
		this.title = title;
		this.content = content;
		this.videoDirectory = videoDirectory;
		this.thumbnailDirectory = thumbnailDirectory;
		this.category = category;
		this.memberId = memberId;
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

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "CommunityRequestDto [type=" + type + ", title=" + title + ", content=" + content + ", videoDirectory="
				+ videoDirectory + ", thumbnailDirectory=" + thumbnailDirectory + ", category=" + category
				+ ", memberId=" + memberId + "]";
	}
    
}
