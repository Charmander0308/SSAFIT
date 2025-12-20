package com.ssafit.video.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class YouTubeVideoDataDto {
	@Schema(description = "유튜브 영상 제목")
	private String title;
	@Schema(description = "유튜브 영상 고유 id")
	private String youtubeId;
	@Schema(description = "유튜브 채널명")
	private String channelName;
	@Schema(description = "유튜브 조회 수")
	private Long viewCnt;
	@Schema(description = "분류")
	private Integer category;
	
	public YouTubeVideoDataDto() {}

	public YouTubeVideoDataDto(String title, String youtubeId, String channelName, Long viewCnt, Integer category) {
		super();
		this.title = title;
		this.youtubeId = youtubeId;
		this.channelName = channelName;
		this.viewCnt = viewCnt;
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYoutubeId() {
		return youtubeId;
	}

	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Long getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(Long viewCnt) {
		this.viewCnt = viewCnt;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "YouTubeVideoDataDto [title=" + title + ", youtubeId=" + youtubeId + ", channelName=" + channelName
				+ ", viewCnt=" + viewCnt + ", category=" + category + "]";
	}
	
}
