package com.ssafit.ai.dto;

public class AIRecommendPostDto {
	private Long id;
	private String title;
	private String contentPart;
	
	public AIRecommendPostDto() {}

	public AIRecommendPostDto(Long id, String title, String contentPart) {
		super();
		this.id = id;
		this.title = title;
		this.contentPart = contentPart;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentPart() {
		return contentPart;
	}

	public void setContentPart(String contentPart) {
		this.contentPart = contentPart;
	}

	@Override
	public String toString() {
		return "AIRecommendPostDto [id=" + id + ", title=" + title + ", contentPart=" + contentPart + "]";
	}
	
}
