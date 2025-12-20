package com.ssafit.config.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafit.global.config.jwt.JwtProperties;
import com.ssafit.global.config.jwt.JwtTokenProvider;
import com.ssafit.member.dao.MemberDao;

@SpringBootTest
public class TokenProviderTest {
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private JwtProperties jwtProperties;
	
	@DisplayName("generateToken()")
	@Test
	void generateToken() {
		//given
		
		//when
		
		//then
		
		
		
	}
}
