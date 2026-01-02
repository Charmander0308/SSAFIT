package com.ssafit.challenge.dto;

import java.time.LocalDateTime;

public class ChallengeResponseDto {
	private Long id;
	private String challengeName;
	private String challengeInfo;
	private String challengeIconDirectory;
	private String challengeTarget;
	private Integer challengeGoal;
	private LocalDateTime achieveDate;
	
	public ChallengeResponseDto() {}

	public ChallengeResponseDto(Long id, String challengeName, String challengeInfo, String challengeIconDirectory,
			String challengeTarget, Integer challengeGoal, LocalDateTime achieveDate) {
		super();
		this.id = id;
		this.challengeName = challengeName;
		this.challengeInfo = challengeInfo;
		this.challengeIconDirectory = challengeIconDirectory;
		this.challengeTarget = challengeTarget;
		this.challengeGoal = challengeGoal;
		this.achieveDate = achieveDate;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChallengeName() {
		return challengeName;
	}

	public void setChallengeName(String challengeName) {
		this.challengeName = challengeName;
	}

	public String getChallengeInfo() {
		return challengeInfo;
	}

	public void setChallengeInfo(String challengeInfo) {
		this.challengeInfo = challengeInfo;
	}

	public String getChallengeIconDirectory() {
		return challengeIconDirectory;
	}

	public void setChallengeIconDirectory(String challengeIconDirectory) {
		this.challengeIconDirectory = challengeIconDirectory;
	}

	public String getChallengeTarget() {
		return challengeTarget;
	}

	public void setChallengeTarget(String challengeTarget) {
		this.challengeTarget = challengeTarget;
	}

	public Integer getChallengeGoal() {
		return challengeGoal;
	}

	public void setChallengeGoal(Integer challengeGoal) {
		this.challengeGoal = challengeGoal;
	}

	public LocalDateTime getAchieveDate() {
		return achieveDate;
	}

	public void setAchieveDate(LocalDateTime achieveDate) {
		this.achieveDate = achieveDate;
	}

	@Override
	public String toString() {
		return "ChallengeResponseDto [challengeName=" + challengeName + ", challengeInfo=" + challengeInfo
				+ ", challengeIconDirectory=" + challengeIconDirectory + ", challengeGoal=" + challengeGoal
				+ ", achieveDate=" + achieveDate + "]";
	}
}
