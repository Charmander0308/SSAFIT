package com.ssafit.config.jwt;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

public class JwtMocking {
	private String subject = "HelloSSAFY";
	private Date issuedAt = new Date();
	private Date expiration = new Date(new Date().getTime() + Duration.ofDays(7).toMillis());
	private Map<String, Object> claims = Map.of();
	
	private JwtMocking(Builder builder) {
		this.subject = (subject != null ? subject : this.subject);
		this.issuedAt = (issuedAt != null ? issuedAt : this.issuedAt);
		this.expiration = (expiration != null ? expiration : this.expiration);
		this.claims = (claims != null ? claims : this.claims);
	}

	public String getSubject() {
		return subject;
	}

	public Date getIssuedAt() {
		return issuedAt;
	}

	public Date getExpiration() {
		return expiration;
	}

	public Map<String, Object> getClaims() {
		return claims;
	}
	
	public static class Builder {
		//사용자가 값 안넣었을 때 null 체크 피하는 용
		private String subject = "HelloSSAFY";
		private Date issuedAt = new Date();
		private Date expiration = new Date(new Date().getTime() + Duration.ofDays(7).toMillis());
		private Map<String, Object> claims = Map.of();
		
		public Builder setSubject(String subject) {
			this.subject = subject;
			return this;
		}
		public Builder setIssuedAt(Date issuedAt) {
			this.issuedAt = issuedAt;
			return this;
		}
		public Builder setExpiration(Date expiration) {
			this.expiration = expiration;
			return this;
		}
		public Builder setClaims(Map<String, Object> claims) {
			this.claims = claims;
			return this;
		}
		public JwtMocking build() {
			return new JwtMocking(this);
		}
		
	}
	
	
	
}
