package com.ssafit.video.dao;

import java.util.List;

import com.ssafit.ai.dto.AIRecommendVideoDto;
import com.ssafit.ai.dto.AIRecommendationDto;
import com.ssafit.video.dto.VideoDto;
import com.ssafit.video.dto.YouTubeVideoDataDto;

public interface VideoDao {
	
	public Integer getVideoCntByCategory(Integer category);
	//서버시작시 비디오 조회 명령
	//서버 지속시 비디오 조회 명령
	//비디오 검색 결과 조회(api연결)
	//비디오 검색 결과 dto에 담기
	//비디오 id 문자열로 변환
	//비디오 정보 조회
	//비디오 정보 조회 결과 dto에 담기
	//비디오 저장(write)

	public void insertVideo(List<YouTubeVideoDataDto> list);
	
	public List<VideoDto> selectVideosByCategoryId(Integer categoryId);
	public List<VideoDto> selectVideos();

	public List<VideoDto> selectRandomVideo(int code);
	
	public String selectVideoIdById(Long id);

	public List<AIRecommendVideoDto> recommendVideosByCategoryId(Integer category);
}
