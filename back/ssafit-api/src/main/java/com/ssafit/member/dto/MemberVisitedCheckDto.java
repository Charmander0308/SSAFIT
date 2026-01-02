package com.ssafit.member.dto;

import java.time.LocalDateTime;

public class MemberVisitedCheckDto {
	private Long id;
	private LocalDateTime lastLoginDate;
	private Integer totalVisited;
	private Integer continuousVisited;
	
	public MemberVisitedCheckDto() {
	}

	public MemberVisitedCheckDto(Long id, LocalDateTime lastLoginDate, Integer totalVisited,
			Integer continuousVisited) {
		this.id = id;
		this.lastLoginDate = lastLoginDate;
		this.totalVisited = totalVisited;
		this.continuousVisited = continuousVisited;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(LocalDateTime lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getTotalVisited() {
		return totalVisited;
	}

	public void setTotalVisited(Integer totalVisited) {
		this.totalVisited = totalVisited;
	}

	public Integer getContinuousVisited() {
		return continuousVisited;
	}

	public void setContinuousVisited(Integer continuousVisited) {
		this.continuousVisited = continuousVisited;
	}
	
	
}
