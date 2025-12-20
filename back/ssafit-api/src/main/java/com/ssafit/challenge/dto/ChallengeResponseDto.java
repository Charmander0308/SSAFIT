package com.ssafit.challenge.dto;

import java.time.LocalDateTime;

public class ChallengeResponseDto {
	private String challengeName;
	private String challengeInfo;
	private String challengeIconDirectory;
	private Integer challengeGoal;
	private LocalDateTime achieveDate;
	
	public ChallengeResponseDto() {}

	public ChallengeResponseDto(String challengeName, String challengeInfo, String challengeIconDirectory,
			Integer challengeGoal, LocalDateTime achieveDate) {
		super();
		this.challengeName = challengeName;
		this.challengeInfo = challengeInfo;
		this.challengeIconDirectory = challengeIconDirectory;
		this.challengeGoal = challengeGoal;
		this.achieveDate = achieveDate;
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
