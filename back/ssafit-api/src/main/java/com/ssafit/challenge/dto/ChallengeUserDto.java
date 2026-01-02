package com.ssafit.challenge.dto;

import java.util.List;

public class ChallengeUserDto {
	private List<ChallengeAchievedDto> achieved;
	private List<ChallengingDto> challenging;

	public ChallengeUserDto() {
	}

	public ChallengeUserDto(List<ChallengeAchievedDto> achieved, List<ChallengingDto> challenging) {
		this.achieved = achieved;
		this.challenging = challenging;
	}

	public List<ChallengeAchievedDto> getAchieved() {
		return achieved;
	}

	public void setAchieved(List<ChallengeAchievedDto> achieved) {
		this.achieved = achieved;
	}

	public List<ChallengingDto> getChallenging() {
		return challenging;
	}

	public void setChallenging(List<ChallengingDto> challenging) {
		this.challenging = challenging;
	}
	
}
