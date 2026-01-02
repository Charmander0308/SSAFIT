package com.ssafit.ai.dto;

import java.util.List;

public class AIRecommendationDto {
	private List<AIRecommendVideoDto> videoList;
	private List<AIRecommendPostDto> postList;
	
	public AIRecommendationDto() {}

	public AIRecommendationDto(List<AIRecommendVideoDto> videoList, List<AIRecommendPostDto> postList) {
		super();
		this.videoList = videoList;
		this.postList = postList;
	}

	public List<AIRecommendVideoDto> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<AIRecommendVideoDto> videoList) {
		this.videoList = videoList;
	}

	public List<AIRecommendPostDto> getPostList() {
		return postList;
	}

	public void setPostList(List<AIRecommendPostDto> postList) {
		this.postList = postList;
	}
	
}
