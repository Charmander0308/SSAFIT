package com.ssafit.challenge.dto;

public class ChallengingDto {
	private Long id;
	private String challengeName;
	private String challengeInfo;
	private String challengeIconDirectory;
	private String challenge_target;
	private Integer challenge_goal;
	private Integer userChallengingStatus;
	private Integer achievementRate;
	
	public ChallengingDto() {
		super();
	}

	public ChallengingDto(Long id, String challengeName, String challengeInfo, String challengeIconDirectory,
			String challenge_target, Integer challenge_goal, Integer userChallengingStatus, Integer achievementRate) {
		this.id = id;
		this.challengeName = challengeName;
		this.challengeInfo = challengeInfo;
		this.challengeIconDirectory = challengeIconDirectory;
		this.challenge_target = challenge_target;
		this.challenge_goal = challenge_goal;
		this.userChallengingStatus = userChallengingStatus;
		this.achievementRate = achievementRate;
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

	public String getChallenge_target() {
		return challenge_target;
	}

	public void setChallenge_target(String challenge_target) {
		this.challenge_target = challenge_target;
	}

	public Integer getChallenge_goal() {
		return challenge_goal;
	}

	public void setChallenge_goal(Integer challenge_goal) {
		this.challenge_goal = challenge_goal;
	}

	public Integer getUserChallengingStatus() {
		return userChallengingStatus;
	}

	public void setUserChallengingStatus(Integer userChallengingStatus) {
		this.userChallengingStatus = userChallengingStatus;
	}

	public Integer getAchievementRate() {
		return achievementRate;
	}

	public void setAchievementRate(Integer achievementRate) {
		this.achievementRate = achievementRate;
	}
	
}
