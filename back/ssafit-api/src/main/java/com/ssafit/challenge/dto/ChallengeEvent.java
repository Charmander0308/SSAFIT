package com.ssafit.challenge.dto;

public class ChallengeEvent {
	private final String domain;
	private final Long memberId;

	public ChallengeEvent(String domain, Long memberId) {
		this.domain = domain;
		this.memberId = memberId;
	}

	public String getDomain() {
		return domain;
	}

	public Long getMemberId() {
		return memberId;
	}

	@Override
	public String toString() {
		return "ChallengeEvent [domain=" + domain + ", memberId=" + memberId + "]";
	}
	
}
