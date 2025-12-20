package com.ssafit.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

@Configuration
public class YouTubeApiConfig {
	@Bean
	public YouTube youtube() throws Exception{
		return new YouTube.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(),
				null
		).setApplicationName("youTube").build();
	}
}
