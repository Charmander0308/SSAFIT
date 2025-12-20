package com.ssafit.video.dto;

import java.time.LocalDateTime;

import com.google.api.client.util.DateTime;

public class VideoDetailResponseDto {
	private String videoId;
	private String thumbnailUrl;
	private String title;
	private DateTime publishedAt;
	
	private String channelTitle;
	private String channelThumbnailUrl;
	private Long viewCount;
	private Long likeCount;

	public VideoDetailResponseDto() {}

	public VideoDetailResponseDto(String videoId, String thumbnailUrl, String title, DateTime publishedAt,
			String channelTitle, String channelThumbnailUrl, Long viewCount, Long likeCount) {
		super();
		this.videoId = videoId;
		this.thumbnailUrl = thumbnailUrl;
		this.title = title;
		this.publishedAt = publishedAt;
		this.channelTitle = channelTitle;
		this.channelThumbnailUrl = channelThumbnailUrl;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DateTime getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(DateTime publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getChannelTitle() {
		return channelTitle;
	}

	public void setChannelTitle(String channelTitle) {
		this.channelTitle = channelTitle;
	}

	public String getChannelThumbnailUrl() {
		return channelThumbnailUrl;
	}

	public void setChannelThumbnailUrl(String channelThumbnailUrl) {
		this.channelThumbnailUrl = channelThumbnailUrl;
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	@Override
	public String toString() {
		return "VideoDetailResponseDto [videoId=" + videoId + ", thumbnailUrl=" + thumbnailUrl + ", title=" + title
				+ ", publishedAt=" + publishedAt + ", channelTitle=" + channelTitle + ", channelThumbnailUrl="
				+ channelThumbnailUrl + ", viewCount=" + viewCount + ", likeCount=" + likeCount + "]";
	}
}
