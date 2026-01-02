package com.ssafit.ai.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafit.ai.dto.AIRecommendPostDto;
import com.ssafit.ai.dto.AIRecommendVideoDto;
import com.ssafit.ai.dto.AIRecommendationDto;
import com.ssafit.community.dao.CommunityDao;
import com.ssafit.video.dao.VideoDao;


@Transactional
@Service
public class AIService {
	private final String aiKey;
	private final RestTemplate restTemplate;
	private final String GMS_URL = "https://gms.ssafy.io/gmsapi/generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=";
	private final ObjectMapper objectMapper;
	private final VideoDao videoDao;
	private final CommunityDao communityDao;
	
	public AIService(@Value("${gemini.api.key}")String aiKey, RestTemplate restTemplate, ObjectMapper objectMapper, 
			VideoDao videoDao, CommunityDao communityDao) {
		this.aiKey = aiKey;
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		this.videoDao = videoDao;
		this.communityDao = communityDao;
	}

	//메인 페이지 ai 추천영상 조회
	public AIRecommendationDto getMainRecommend() {
		List<AIRecommendVideoDto> videos = getVideoRecommend(null);
		List<AIRecommendPostDto> posts = getPostRecommend(null);
		AIRecommendationDto recommends = new AIRecommendationDto(videos, posts); 
		return recommends;
	}
	
	//비디오 페이지 ai 추천영상 조회
	public List<AIRecommendVideoDto> getVideoRecommend(Integer category) {
		//전달할 데이터 추출 ->데이터를 다 넘길경우 api가 터집니다... 일부만 rand로 추출해서 넘길게요..
		List<AIRecommendVideoDto> videoList = videoDao.recommendVideosByCategoryId(category);
		//데이터 넣어서 프롬프트 작성
		String prompt = getVideoPrompt(videoList);
		//ai에게 응답받기
		String response = getAiResponse(prompt);
		//응답 파싱하기
		List<AIRecommendVideoDto> videos = convertAiResponseToList(response, AIRecommendVideoDto.class);
		return videos;
	}
	
	//게시글 용 ai 추천영상 조회
	public List<AIRecommendPostDto> getPostRecommend(Integer category){
		List<AIRecommendPostDto> postList = communityDao.recommendCommunityByCategoryId(category);
		String prompt = getPostPrompt(postList);
		String response = getAiResponse(prompt);
		List<AIRecommendPostDto> posts = convertAiResponseToList(response, AIRecommendPostDto.class);
		return posts;

	}
	
	//ai에게 전달할 프롬프트 작성용 메서드
	//비디오용
	public String getVideoPrompt(List<AIRecommendVideoDto> list) {
		String dataString  = list.stream()
				.map(dto -> "{id=" + dto.getId() + ", youtubeId=" + dto.getYoutubeId() + ", title=" + dto.getTitle()+"}")
				.collect(Collectors.joining(", "));
		String prompt = String.format(
			    "Context: You are a fitness and health expert. Analyze the following list of fitness videos and select the 5 most effective ones based on their titles.\n" +
			    	    "DATA LIST: [%s]\n\n" +
			    	    "Task:\n" +
			    	    "1. Pick exactly 5 unique and highly effective videos.\n" +
			    	    "2. Return the result as a JSON array of objects.\n" +
			    	    "3. Each object must include 'id', 'youtubeId', and 'title'.\n\n" +
			    	    "Constraint:\n" +
			    	    "Return ONLY the raw JSON array. No markdown, no code blocks (```), no backticks, and no conversational text.",
			    	    dataString
			    	);
		return prompt;
	}
	//게시글용
	public String getPostPrompt(List<AIRecommendPostDto> list) {
		String dataString  = list.stream()
				.map(dto -> "{id=" + dto.getId() + ", title=" + dto.getTitle() + ", contentPart=" + dto.getContentPart()+"}")
				.collect(Collectors.joining(", "));
		String prompt = String.format(
			    "Context: You are a fitness and health expert. Analyze the following list of fitness posts.\n" +
			    "DATA LIST: [%s]\n\n" +
			    "Task:\n" +
			    "1. Evaluate each post by reading its 'title' and 'contentPart'.\n" +
			    "2. Select exactly 5 unique posts that are most professional and helpful for fitness.\n" +
			    "3. Return the result as a JSON array of objects.\n" +
			    "4. Each object in the array MUST contain these keys: \"id\", \"title\", and \"contentPart\".\n\n" +
			    "Constraint:\n" +
			    "- Return ONLY the raw JSON array.\n" +
			    "- Do NOT include any markdown code blocks (```), backticks, or conversational filler.\n" +
			    "- Ensure the JSON is valid and can be parsed immediately.",
			    dataString
			);
		return prompt;
	}

	
	//ai에게 프롬프트 전달 -> 응답받기
	public String getAiResponse(String prompt) {
		//요청 주소 만들기
		String url = GMS_URL + aiKey;
		
		// 1. Gemini 요청 구조 정의 (내부 클래스!!!!! 라이브러리 객체가 아닙니다!!!!!!! 바로 아래 보면 있어요)
		GeminiRequest requestBody = new GeminiRequest(prompt);
		System.out.println(requestBody);
		
		// 2. 요청 실행 
		//RestTemplate : 스프링 프레임워크에서 API에 요청할 때 사용.. AIConfig에 bean등록 해두었습니다.
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
			e.printStackTrace();
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
	
	//ai에게 받은 응답 파싱해서 List로 변환
	//하나의 메서드에서 2개의 반환타입을 가지기를 원했음
	private <T> List<T> convertAiResponseToList(String aiResponse, Class<T> targetClass) {
	    try {
	        // 1. JSON 문자열 정제
	        String cleanedJson = aiResponse.replaceAll("```json|```", "").trim();
	        
	        // 2. Jackson(objectMapper)을 사용하여 동적으로 리스트 타입 생성
	        // 이 부분이 핵심입니다. TypeReference 대신 JavaType을 사용합니다.
	        return objectMapper.readValue(cleanedJson, 
	            objectMapper.getTypeFactory().constructCollectionType(List.class, targetClass));
	            
	    } catch (Exception e) {
	        System.err.println(targetClass.getSimpleName() + " 파싱 에러: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
}
