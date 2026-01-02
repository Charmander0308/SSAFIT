package com.ssafit.video.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.ssafit.category.type.Category;
import com.ssafit.challenge.dto.Domains;
import com.ssafit.comment.service.CommentService;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;
import com.ssafit.video.dao.VideoDao;
import com.ssafit.video.dto.VideoDetailResponseDto;
import com.ssafit.video.dto.VideoDto;
import com.ssafit.video.dto.VideoListResponseDto;
import com.ssafit.video.dto.YouTubeVideoDataDto;

@Service
@Transactional
public class VideoService {
	private final YouTube youTube;
	private final String apiKey;
	private final VideoDao videoDao;
	private final String aiKey;
	private final RestTemplate restTemplate;
	private final String GMS_URL = "https://gms.ssafy.io/gmsapi/generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=";
	private final CommentService commentService;
	
	public VideoService(YouTube youTube, @Value("${youtube.api.key}") String apiKey, VideoDao videoDao, @Value("${gemini.api.key}")String aiKey
			,RestTemplate restTemplate ,CommentService commentService) {
		this.youTube = youTube;
		this.apiKey = apiKey;
		this.videoDao = videoDao;
		this.aiKey = aiKey;
		this.restTemplate = restTemplate;
		this.commentService = commentService;
	}
	private Category[] category = Category.values();
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	//목차
	//유튜브 API연결 ~ DB에 저장하는 메서드
	//AI 관련 메서드
	//비디오 list페이지 관련 메서드
	//비디오 detail 페이지 관련 메서드
	
	
	//서버 시작시 비디오 목록 가져오는 메서드: 가장 상위 -> 각 카테고리 별로 writeVideo실행
	//데이터가 50개씩(엉덩이 운동은 25개) 밖에 안온다....
	public void getFirstVideoData() {
		//카테고리별로 동영상 가져오기
		for(Category c : category) {
			//데이터가 이미 존재하는 경우 통과
			if(videoDao.getVideoCntByCategory(c.getCode()) >= 100) {System.out.println("건너뜀");continue;}
			//데이터가 없을 경우 카테고리별로 비디오 조회 및 저장
			List<YouTubeVideoDataDto> dtoList = new ArrayList<YouTubeVideoDataDto>();
			//데이터 조회+데이터 추출
			List<SearchResult> videoSearchList = getVideoSearchList(c.getLabel(), 1000);
			setVideoSearchData(videoSearchList, dtoList, c.getCode());
			//조회수 가져와서 담는 통합메서드
			setVideoViewData(dtoList);
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
			List<SearchResult> videoSearchList = getVideoSearchList(c.getLabel(), 100);
			setVideoSearchData(videoSearchList, dtoList, c.getCode());
			//조회수 가져와서 담는 통합메서드
			setVideoViewData(dtoList);
			//DB에 데이터 저장(insert)
			writeVideo(dtoList);
			
		}
	}
	
	
	//카테고리에 맞게 비디오 검색하는 메서드
	//검색결과 list를 받아서 상위 메서드에 반환
	private List<SearchResult> getVideoSearchList(String category, int num) {
		try {
			List<SearchResult> result = new ArrayList<SearchResult>();
			String pageToken = null;
			while(num > 0) {
				YouTube.Search.List search = youTube.search().list("id,snippet");
				search.setKey(apiKey)
				.setFields("nextPageToken,items(id/videoId,snippet/title,snippet/channelTitle)")
				.setMaxResults(50L)
				.setSafeSearch("moderate")
				.setQ(category+" 운동 workout")
				.setOrder("viewCount")
				.setPageToken(pageToken)
				.setType("video");
				SearchListResponse searchList = search.execute();
				result.addAll(searchList.getItems()); 
				pageToken = searchList.getNextPageToken();
				if (pageToken == null) break;
				num -= 50;
			}
		return result;
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

	//비디오 조회수를 50개씩 가져와 담는 통합 메서드
	private void setVideoViewData(List<YouTubeVideoDataDto> dtoList) {
		int check = 0, size = dtoList.size();
		while(check < size) {
			int end = Math.min(check + 50, size);
			String idString = setVideoIdToString(dtoList.subList(check, end));
			List<Video> videos = getVideoViews(idString);
			setVideoViews(videos, dtoList);
			
			check += 50;
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
	
	
	//비디오 정보 조회 결과 dto에 담는 메서드..........................
	//이제는 이것 말고는 방법이 없다
	private void setVideoViews(List<Video> videoList, List<YouTubeVideoDataDto> videoDtoList){
		Map<String, Video> videoMap = new HashMap<>();
	    for (Video v : videoList) {
	        videoMap.put(v.getId(), v);
	    }

	    // 2. DTO 리스트를 돌면서 Map에서 ID로 꺼내와 세팅
	    for (YouTubeVideoDataDto dto : videoDtoList) {
	        Video videoData = videoMap.get(dto.getYoutubeId());
	        try {
		        if (videoData != null && videoData.getStatistics() != null) {
		            dto.setViewCnt(videoData.getStatistics().getViewCount().longValue());
		        }
	        } catch (NullPointerException e) {
	        	dto.setViewCnt(0L);
	        }
	    }
	} 
	
	
	//DB에 저장하는 메서드
	private void writeVideo(List<YouTubeVideoDataDto> list) {
		videoDao.insertVideo(list);
		System.out.println("저장했어요");
	}


	
	
	
	
	//------------------------------------------------------------------------------------------------
	//AI관련 메서드 시작
	
	//비디오 리스트 화면에서 ai가 비디오 추천해주는 메서드
	public List<VideoDto> getRecommendedVideoIds(Integer category) {
        List<VideoDto> videoList = videoDao.selectVideosByCategoryId(category);

        if (videoList.isEmpty()) return Collections.emptyList();

        //프롬프트(String을 작성할 겁니다)
        //우선 데이터를 담을 String을 만들어 보겠습니다.
        String dataString = videoList.stream()
        		.map(dto -> "{id=" + dto.getId() + ", youtubeId=" + dto.getYoutubeId() + "}")
        	    .collect(Collectors.joining(", "));
        
        //프롬프트 작성하기
        String prompt = String.format(
        		"Context: I have a database of fitness videos. Below is a list of video data objects.\n" +
        		        "DATA LIST: [%s]\n\n" +
        		        "Task:\n" +
        		        "1. Analyze the videos and pick exactly 5 unique videos that are most suitable for category ID %d.\n" +
        		        "2. You must return the result as a JSON array of objects, where each object contains BOTH the 'id' and 'youtubeId'.\n\n" +
        		        "Output Format Example:\n" +
        		        "[{\"id\": 1, \"youtubeId\": \"vid1\"}, {\"id\": 5, \"youtubeId\": \"vid2\"}, ...]\n\n" +
        		        "Constraint: Return ONLY the raw JSON array. Do not include markdown formatting, backticks, or any conversational text.",
        		        dataString, category
            );
        
        
        //AI에게 프롬프트 전달(나중에  AI MVC별도로 빼고싶다......)
        String aiResponse = getAiResponse(prompt);

        // 4. AI가 준 JSON 문자열을 List<String>으로 파싱하여 반환
        try {
            // AI가 ```json ... ``` 형식으로 응답할 경우를 대비해 정제
            String cleanedJson = aiResponse.replaceAll("```json|```", "").trim();
            
            // JSON 배열 문자열을 List<String>으로 변환
            return objectMapper.readValue(cleanedJson, new TypeReference<List<VideoDto>>() {});
        } catch (Exception e) {
            System.err.println("JSON 파싱 에러: " + e.getMessage());
            // 에러 발생 시 fallback: DB 리스트 중 랜덤하게 5개 반환
            Collections.shuffle(videoList);
            return videoList.subList(0, Math.min(videoList.size(), 5));
        }
    }
    
	

	
	//제미나이에게 프롬프트로 요청 전달 -> 응답 받기
	public String getAiResponse(String prompt) {
		//요청 주소 만들기
		String url = GMS_URL + aiKey;
		
		// 1. Gemini 요청 구조 정의 (내부 클래스!!!!! 라이브러리 객체가 아닙니다!!!!!!! 바로 아래 보면 있어요)
		GeminiRequest requestBody = new GeminiRequest(prompt);
		
		// 2. 요청 실행 
		//RestTemplate : 스프링 프레임워크에서 API에 요청할 때 사용한대요.. AIConfig에 bean등록 해두었습니다.
		try {
			String response = restTemplate.postForObject(url, requestBody, String.class);
			
			// 2. ObjectMapper를 이용해 JSON 트리 구조로 파싱합니다.
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode root = objectMapper.readTree(response);
			
			// 3. 경로를 따라가서 text 값만 추출합니다.
			// 경로: candidates[0] -> content -> parts[0] -> text
			String resultText = root.path("candidates")
					.get(0)
					.path("content")
					.path("parts")
					.get(0)
					.path("text")
					.asText();
			
			String result =  resultText.trim();
			return result;
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}
	
	
	// Gemini API 규격에 맞춘 내부 DTO 구조.....
	static class GeminiRequest {
		public List<Content> contents;
		
		public GeminiRequest(String text) {
			this.contents = Collections.singletonList(new Content(text));
		}
		public List<Content> getContents() { return contents; }
		static class Content {
			public List<Part> parts;
			public Content(String text) {
				this.parts = Collections.singletonList(new Part(text));
			}
			public List<Part> getParts() { return parts; }
		}
		
		static class Part {
			public String text;
			public Part(String text) { this.text = text; }
			public String getText() { return text; }
		}
	}


	//메인 화면에서 ai가 비디오 추천해주는 메서드
		public List<VideoDto> getMainRecommended() {
	        List<VideoDto> videoList = videoDao.selectVideos();

	        if (videoList.isEmpty()) return Collections.emptyList();

	        //프롬프트(String을 작성할 겁니다)
	        //우선 데이터를 담을 String을 만들어 보겠습니다.
	        String dataString = videoList.stream()
	        		.map(dto -> "{id=" + dto.getId() + ", youtubeId=" + dto.getYoutubeId() + "}")
	        	    .collect(Collectors.joining(", "));
	        
	        //프롬프트 작성하기
	        String prompt = String.format(
	        		"Context: I have a database of fitness videos. Below is a list of video data objects.\n" +
	        		        "DATA LIST: [%s]\n\n" +
	        		        "Task:\n" +
	        		        "1. Analyze the videos and pick exactly 5 unique videos.\n" +
	        		        "2. You must return the result as a JSON array of objects, where each object contains BOTH the 'id' and 'youtubeId'.\n\n" +
	        		        "Output Format Example:\n" +
	        		        "[{\"id\": 1, \"youtubeId\": \"vid1\"}, {\"id\": 5, \"youtubeId\": \"vid2\"}, ...]\n\n" +
	        		        "Constraint: Return ONLY the raw JSON array. Do not include markdown formatting, backticks, or any conversational text.",
	        		        dataString
	            );
	        
	        
	        //AI에게 프롬프트 전달(나중에  AI MVC별도로 빼고싶다......)
	        String aiResponse = getAiResponse(prompt);

	        // 4. AI가 준 JSON 문자열을 List<String>으로 파싱하여 반환
	        try {
	            // AI가 ```json ... ``` 형식으로 응답할 경우를 대비해 정제
	            String cleanedJson = aiResponse.replaceAll("```json|```", "").trim();
	            
	            // JSON 배열 문자열을 List<String>으로 변환
	            return objectMapper.readValue(cleanedJson, new TypeReference<List<VideoDto>>() {});
	        } catch (Exception e) {
	            System.err.println("JSON 파싱 에러: " + e.getMessage());
	            // 에러 발생 시 fallback: DB 리스트 중 랜덤하게 5개 반환
	            Collections.shuffle(videoList);
	            return videoList.subList(0, Math.min(videoList.size(), 5));
	        }
	    }
	    
	
	
	
	
	//---------------------------------------------------------------------------------------------
	//비디오 list관련 메서드
	//화면 접속시 카테고리당 5개씩 동영상 추출해주는 서비스메서드
	public List<VideoListResponseDto> getVideoList() {
		List<VideoListResponseDto> videoList = new ArrayList<>();
		for(Category c : category) {
			VideoListResponseDto videos = new VideoListResponseDto();
			videos.setCategory(c.getLabel());
			videos.setVideoIds(videoDao.selectRandomVideo(c.getCode()));
			videoList.add(videos);
		}
		return videoList;
	}
	
	
	//------------------------------------------------------------------------------------------------
	//비디오 정보 조회 메서드
	
	public VideoDetailResponseDto getVideo(Long id) {
		String videoId = videoDao.selectVideoIdById(id);
		VideoDetailResponseDto videoDetail = getVideoDetail(videoId);
		videoDetail.setId(id);
		videoDetail.setCommentCnt(commentService.getCommentCnt(Domains.VIDEO.getDomain(), id));
		videoDetail.setComments(commentService.getCommentListByDomain(Domains.VIDEO.getDomain(), id));
		return videoDetail;
	}
	
		
	//단일 비디오 조회 시 유튜브 api를 통해 비디오 정보를 반환
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
