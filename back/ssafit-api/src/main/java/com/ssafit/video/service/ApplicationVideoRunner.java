package com.ssafit.video.service;

import java.io.IOException;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;

@Component
public class ApplicationVideoRunner implements ApplicationRunner{
	private final VideoService videoService;
	public ApplicationVideoRunner(VideoService videoService) {
		this.videoService = videoService;
	}
	@Override
	public void run(ApplicationArguments args) {
			videoService.getFirstVideoData();
	}
}
