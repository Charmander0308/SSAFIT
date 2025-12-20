package com.ssafit.global.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafit.global.config.jwt.JwtAuthenticationFilter;
import com.ssafit.global.config.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig {
	
	private final JwtTokenProvider jwtTokenProvider;
	
	public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	//완성되면 지우기
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
				.requestMatchers("/resources/**", "/swagger-ui/**", "/v3/api-docs/**");
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
		//완성되면 지우기
		.authorizeHttpRequests(auth -> auth
				.anyRequest().permitAll())
		//완성되면 이걸로 변경
//		.authorizeHttpRequests(auth -> auth
//				.requestMatchers(
//						"/resources/**",
//						"/v3/api-docs/**",
//						"/swagger-ui/**",
//						
//						"/api/index",
//						"/.api/login",
//						"/api/member/signup"
//						).permitAll()
//				.requestMatchers("/api/admin/**").hasRole("ADMIN")	//관리자페이지가 완성본에 있다면..
//				.anyRequest().authenticated())
		
		.csrf(csrf -> csrf.disable())
		
		.formLogin(form -> form.disable())
		
		.httpBasic(httpBasic -> httpBasic.disable())
		
		.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		
		//jwt filter 만들어서 끼우기
		.addFilterBefore(
				new JwtAuthenticationFilter(jwtTokenProvider), 
				UsernamePasswordAuthenticationFilter.class
		)
			
		.build();
		
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
