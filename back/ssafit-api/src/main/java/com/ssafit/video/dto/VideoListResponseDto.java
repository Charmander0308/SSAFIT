package com.ssafit.video.dto;

import java.util.List;

public class VideoListResponseDto {
	private String category;
	private List<VideoDto> videoIds;
	
	public VideoListResponseDto() {}

	public VideoListResponseDto(String category, List<VideoDto> videoIds) {
		super();
		this.category = category;
		this.videoIds = videoIds;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<VideoDto> getVideoIds() {
		return videoIds;
	}

	public void setVideoIds(List<VideoDto> videoIds) {
		this.videoIds = videoIds;
	}

	@Override
	public String toString() {
		return "VideoListResponseDto [category=" + category + ", videoIds=" + videoIds + "]";
	}

	
}
