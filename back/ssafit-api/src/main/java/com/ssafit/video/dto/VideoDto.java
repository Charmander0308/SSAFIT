package com.ssafit.video.dto;

public class VideoDto {
	private int id;
	private String youtubeId;
	public VideoDto() {}
	public VideoDto(int id, String youtubeId) {
		super();
		this.id = id;
		this.youtubeId = youtubeId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getYoutubeId() {
		return youtubeId;
	}
	public void setYoutubeId(String youtubeId) {
		this.youtubeId = youtubeId;
	}
	@Override
	public String toString() {
		return "{id=" + id + ", youtubeId=" + youtubeId + "}";
	}
	
	
}
