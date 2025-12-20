package com.ssafit.challenge.dto;

public class ChallengeRequestDto {
	private Long challengeId;
	private Long memberId;
	
	public ChallengeRequestDto() {}

	public ChallengeRequestDto(Long challengeId, Long memberId) {
		super();
		this.challengeId = challengeId;
		this.memberId = memberId;
	}

	public Long getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(Long challengeId) {
		this.challengeId = challengeId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "ChallengeRequestDto [challengeId=" + challengeId + ", memberId=" + memberId + "]";
	}
	
}
