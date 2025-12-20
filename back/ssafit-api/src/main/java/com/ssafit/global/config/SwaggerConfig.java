package com.ssafit.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info().title("SSAFIT API Reference")
						.description("""
	                            <p>SSAFY인들을 위한 종합 건강 관리 플랫폼, SSAFIT의 API 명세서입니다.</p>
	                            <p>AI를 활용한 맞춤형 영상 제공, 회원 관리, 운동 영상 조회 및 업로드, 좋아요 및 리뷰 기능 등을 제공합니다.</p>
	                            <p>본 프로젝트는 RESTful 아키텍처를 준수합니다.</p>
	                            <h3>Developer Team : [예혜라디야]</h3>
	                            <ul>
		                            <li><strong>예성</strong> : <a href="https://github.com/Charmander0308" target="_blank">Github</a></li>
		                            <li><strong>혜원</strong> : <a href="https://github.com/zheldgkwk" target="_blank">Github</a></li>
	                            </ul>
	                            """)
	                    .version("v0.0.1")
	                    .contact(new Contact()
	                            .name("[팀 예혜라디야] 깃허브 바로가기")
	                            .url("#"))
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
