package com.ssafit.ai.dto;

public class AIRecommendVideoDto {
	private Long id;
	private String youtubeId;
	private String title;
	
	public AIRecommendVideoDto() {}

	public AIRecommendVideoDto(Long id, String youtubeId, String title) {
		super();
		this.id = id;
		this.youtubeId = youtubeId;
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYoutubeId() {
		return youtubeId;
	}

	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "AIRecommendVideoDto [id=" + id + ", youtubeId=" + youtubeId + ", title=" + title + "]";
	}
	
}
