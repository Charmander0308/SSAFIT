package com.ssafit.ai.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.ai.dto.AIRecommendVideoDto;
import com.ssafit.ai.dto.AIRecommendationDto;
import com.ssafit.ai.service.AIService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ai")
@Tag(name="AI API", description="AI추천 관련 API")
public class AIController {
 private final AIService aiService;
 public AIController(AIService aiService) {
	 this.aiService = aiService;
 }
 
 @GetMapping("/main")
 public ResponseEntity<AIRecommendationDto> getMainRecommendation(){
	 AIRecommendationDto result = aiService.getMainRecommend();
	 return ResponseEntity.ok(result);
 }
 @GetMapping("/video")
 public ResponseEntity<List<AIRecommendVideoDto>> getVideoRecommendation(@RequestParam("category") Integer category){
	 System.out.println(category);
	 List<AIRecommendVideoDto> result = aiService.getVideoRecommend(category);
	 System.out.println(result);
	 return ResponseEntity.ok(result);
 }
 
}
