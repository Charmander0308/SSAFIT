package com.ssafit.global.config.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.ssafit.member.dto.MemberAuthDto;
import com.ssafit.member.type.MemberRank;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;
	
	public JwtTokenProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}
	
	//호출용
	public String generateAccessToken(MemberAuthDto member) {
		return makeToken(member, Duration.ofHours(1), true);
	}
	
	public String generateRefreshToken(MemberAuthDto member) {
		return makeToken(member, Duration.ofDays(14), false);
	}
	
	//토큰 생성
	private String makeToken(MemberAuthDto member, Duration duration, boolean isAccessToken) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + duration.toMillis());
		
		var builder = Jwts.builder()
				.header().add("typ", "JWT")
				.and()
				
				.subject(String.valueOf(member.getId()))
				.issuer(jwtProperties.getIssuer())
				.issuedAt(now)
				.expiration(expiry);
		//access 토큰인 경우에만 추가정보 넣기
		if(isAccessToken) {
			 builder.claim("userId", member.getUsername())	
					.claim("nickname", member.getNickname())
					.claim("grade", member.getGrade());
		}
		return builder
				.signWith(getSigningKey())
				.compact();
				
	}
	
	//비밀 키의 타입을 String -> SecretKey로 변환
	private SecretKey getSigningKey() {
		byte[] keyBites = jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBites);
	}
	
	//토큰 유효성 검증
	public boolean validToken(String token) {
		try {
			Jwts.parser()
			.verifyWith(getSigningKey())
			.build()
			.parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//토큰으로 인증정보를 반환
	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);

		Integer grade = claims.get("grade", Integer.class);
		if(grade == null) grade = 0;
		
		String role = (grade == MemberRank.ADMIN.getCode()) ? "ROLE_ADMIN" : "ROLE_USER";
		Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(role));
		
		MemberAuthDto memberAuthDto = new MemberAuthDto(
				Long.parseLong(claims.getSubject()),
				claims.get("userId", String.class),
				null,
				null,
				claims.get("nickname", String.class),
				null,
				grade
		);
		
		return new UsernamePasswordAuthenticationToken(memberAuthDto, token, authorities);
	}
	
	//토큰에서 Id(PK) 꺼내기
	public Long getId(String token) {
		return Long.parseLong(getClaims(token).getSubject());
	}
	
	//토큰에서 userID 꺼내기
	public String getUserId(String token) {
		return getClaims(token).get("userId", String.class);
	}

	//토큰에서 grade 꺼내기
	public int getGrade(String token) {
		return getClaims(token).get("grade", Integer.class);
	}
	
	//토큰에서 클레임 모두 꺼내기
	private Claims getClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	
	
}
