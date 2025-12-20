package com.ssafit.video.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.ssafit.category.type.Category;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;
import com.ssafit.video.dao.VideoDao;
import com.ssafit.video.dto.VideoDetailResponseDto;
import com.ssafit.video.dto.YouTubeVideoDataDto;

@Service
@Transactional
public class VideoService {
	private final YouTube youTube;
	private final String apiKey;
	private final VideoDao videoDao;
	public VideoService(YouTube youTube, @Value("${youtube.api.key}") String apiKey, VideoDao videoDao) {
		this.youTube = youTube;
		this.apiKey = apiKey;
		this.videoDao = videoDao;
	}
	private Category[] category = Category.values();
	
	//조회를 할 때 마다 긁어오는 거 -> 긁어오고 db에서 뷰에 전달
	//서버 시작할 때 카테고리별로 1000개 긁어오기 -> 1달에(실제시간기준으로) 카테고리별로 100개씩 추가로 긁어오기
	//AI가 2~3개씩 맞춤으로 보여주는 작업
	
	
	
	//서버 시작시 비디오 목록 가져오는 메서드: 가장 상위 -> 각 카테고리 별로 writeVideo실행
	//데이터가 50개씩(엉덩이 운동은 25개) 밖에 안온다....
	public void getFirstVideoData() {
		//카테고리별로 동영상 가져오기
		for(Category c : category) {
			//데이터가 이미 존재하는 경우 통과
			if(videoDao.getVideoCntByCategory(c.getCode()) != 0) {continue;}
			//데이터가 없을 경우 카테고리별로 비디오 조회 및 저장
			List<YouTubeVideoDataDto> dtoList = new ArrayList<YouTubeVideoDataDto>();
			//데이터 조회+데이터 추출
			List<SearchResult> videoSearchList = getVideoSearchList(c.getLabel(), 1000L);
			setVideoSearchData(videoSearchList, dtoList, c.getCode());
			String idString = setVideoIdToString(dtoList);
			List<Video> videoList = getVideoViews(idString);
			setVideoViews(videoList, dtoList);
			//DB에 데이터 저장(insert)
			writeVideo(dtoList);
		}
	} 
	
	
	//서버 지속시 비디오 목록 가져오는 메서드 -> 카테고리별로 분류하지 않고 통째로 저장
	@Scheduled(cron="0 0 0 1 * *")
	public void getVideoData() {
		List<YouTubeVideoDataDto> dtoList = new ArrayList<YouTubeVideoDataDto>();
		for(Category c : category) {
			//데이터 조회+데이터 추출
			List<SearchResult> videoSearchList = getVideoSearchList(c.getLabel(), 100L);
			setVideoSearchData(videoSearchList, dtoList, c.getCode());
			String idString = setVideoIdToString(dtoList);
			List<Video> videoList = getVideoViews(idString);
			setVideoViews(videoList, dtoList);
			//DB에 데이터 저장(insert)
			writeVideo(dtoList);
		}
	}
	
	
	//카테고리에 맞게 비디오 검색하는 메서드
	//검색결과 list를 받아서 상위 메서드에 반환
	private List<SearchResult> getVideoSearchList(String category, Long num) {
		try {
		YouTube.Search.List search = youTube.search().list("id,snippet");
		search.setKey(apiKey)
		.setFields("items(id/videoId,snippet/title,snippet/channelTitle)")
			.setMaxResults(num)
			.setSafeSearch("moderate")
			.setQ(category+" 운동")
			.setType("video");
		
		SearchListResponse searchList = search.execute(); 
		return searchList.getItems();
		} catch (IOException e) {
			throw new CustomException(ErrorCode.API_CONNECTION_ERROR);
		}
	}
	
	//비디오 검색 결과 dto에 담기
	private void setVideoSearchData(List<SearchResult> videoList, List<YouTubeVideoDataDto> dtoList, Integer category){
		for(SearchResult r : videoList) {
			YouTubeVideoDataDto dto = new YouTubeVideoDataDto();
			dto.setTitle(r.getSnippet().getTitle());
			dto.setYoutubeId(r.getId().getVideoId());
			dto.setChannelName(r.getSnippet().getChannelTitle());
			dto.setCategory(category);
			dtoList.add(dto);
		}
	}

	
	//검색결과에서 id만 추출 -> String으로 전환
	private String setVideoIdToString(List<YouTubeVideoDataDto> videoDtoList) {
		StringBuilder string = new StringBuilder();
		for(int i=0; i<videoDtoList.size(); i++) {
			string.append(videoDtoList.get(i).getYoutubeId());
			if(i<videoDtoList.size()-1) string.append(",");
		}
		return string.toString();
	}
	
	
	//비디오 세부정보 조회하는 메서드
	//비디오 조회수는 검색요청으로는 얻을 수 없으며 id를 통한 비디오 세부조회를 해야 함
	private List<Video> getVideoViews(String videoId){
		try {
			YouTube.Videos.List request = youTube.videos()
				    .list("id,statistics")
				    .setKey(apiKey)
				    .setId(videoId);
				VideoListResponse response = request.execute();
				return response.getItems();
			
		} catch (IOException e) {
			throw new CustomException(ErrorCode.API_CONNECTION_ERROR);
		}
	}
	
	
	//비디오 정보 조회 결과 dto에 담는 메서드
	private void setVideoViews(List<Video> videoList, List<YouTubeVideoDataDto> videoDtoList){
		for(int i=0;  i < videoDtoList.size(); i++) {
			videoDtoList.get(i).setViewCnt(videoList.get(i).getStatistics().getViewCount().longValue());
		}
	} 
	
	//DB에 저장하는 메서드
	private void writeVideo(List<YouTubeVideoDataDto> list) {
		videoDao.insertVideo(list);
	}


	
	
	
	
	//단일 비디오 조회 시 처리(임시보관)
	public VideoDetailResponseDto getVideoDetail(String query) {
		Video video;
		YouTube.Videos.List request;
		try {
			request = youTube.videos()
			    .list("snippet,contentDetails,statistics")
			    .setKey(apiKey)
			    .setId(query);
			VideoListResponse response = request.execute();
			video = response.getItems().get(0);			
			String videoId = video.getId();
			String thumbnailUrl = video.getSnippet().getThumbnails().getDefault().getUrl();
			String title = video.getSnippet().getTitle();
			DateTime publishedAt = video.getSnippet().getPublishedAt();
			
			String channelTitle = video.getSnippet().getChannelTitle();
			String channelId = video.getSnippet().getChannelId();
			Long viewCount = video.getStatistics().getViewCount().longValue();
			Long likeCount = video.getStatistics().getLikeCount().longValue();
			String channelThumbnailUrl = getChannelImg(channelId);
			return new VideoDetailResponseDto(videoId, thumbnailUrl, title, publishedAt, channelTitle, channelThumbnailUrl, viewCount, likeCount);
		} catch (IOException e) {
			throw new CustomException(ErrorCode.API_CONNECTION_ERROR);
		}		
	}
	
	//부가기능이므로 error throw하지 않음
	private String getChannelImg(String channelId) throws IOException {
		YouTube.Channels.List request = youTube.channels()
	            .list("snippet,contentDetails,statistics").setId(channelId).setKey(apiKey);
		ChannelListResponse response = request.execute();
		Channel channel = response.getItems().get(0);
		return channel.getSnippet().getThumbnails().getDefault().getUrl();
	}
	

}
