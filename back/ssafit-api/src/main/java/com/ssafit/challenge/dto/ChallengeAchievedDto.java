package com.ssafit.challenge.dto;

import java.time.LocalDateTime;

public class ChallengeAchievedDto {
	private Long id;
	private String challengeName;
	private String challengeInfo;
	private String challengeIconDirectory;
	private LocalDateTime achieveDate;

	public ChallengeAchievedDto() {
	}

	public ChallengeAchievedDto(Long id, String challengeName, String challengeInfo, String challengeIconDirectory,
			LocalDateTime achieveDate) {
		this.id = id;
		this.challengeName = challengeName;
		this.challengeInfo = challengeInfo;
		this.challengeIconDirectory = challengeIconDirectory;
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

	public LocalDateTime getAchieveDate() {
		return achieveDate;
	}

	public void setAchieveDate(LocalDateTime achieveDate) {
		this.achieveDate = achieveDate;
	}
	
	
}
