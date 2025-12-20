package com.ssafit.video.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.category.type.Category;
import com.ssafit.video.dto.VideoDetailResponseDto;
import com.ssafit.video.dto.YouTubeVideoDataDto;
import com.ssafit.video.service.VideoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/video")
@Tag(name = "동영상 API", description = "유튜브 동영상 관련 API")
public class VideoController {
	private final VideoService videoService;
	public VideoController(VideoService videoService) {
		this.videoService = videoService;
	}
//	
//	@GetMapping
//	public ResponseEntity<List<CategoryVideoDto>> video(){
//		ai로부터 추천영상 받음
//		return new ResponseEntity<List<CategoryVideoDto>>(categoryList, HttpStatus.OK);
//	}
//	
	@GetMapping("/{videoId}")
	@Operation(summary = "동영상 세부내용 조회", description = "선택한 동영상의 세부 정보를 조회한다.")
	public ResponseEntity<VideoDetailResponseDto> videoDetail(
			@Parameter(description = "동영상 id 값", example = "1") @PathVariable("videoId") String videoId) {
		return ResponseEntity.ok(videoService.getVideoDetail(videoId));
	}
}
