package com.ssafit.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 활동정보 DTO")
public class MemberStatusResponseDto {
	@Schema(description = "회원 id 값", example = "1")
	private Long id;
	@Schema(description = "팔로워 수", example = "201")
	private Integer follower;
	@Schema(description = "팔로잉 수", example = "204")
	private Integer following;
	@Schema(description = "사이트 총 방문 일수", example = "31")
	private Integer totalVisited;
	@Schema(description = "사이트 연속 방문 일수", example = "12")
	private Integer continuousVisited;
	@Schema(description = "해당 회원이 등록한 게시물의 총 조회 수", example = "1441")
	private Long totalViews;
	@Schema(description = "해당 회원이 등록한 총 댓글 수", example = "38")
	private Integer totalComments;
	@Schema(description = "회원등급", example = "4")
	private Integer grade;
	
	public MemberStatusResponseDto() {
	}

	public MemberStatusResponseDto(Long id, Integer follower, Integer following, Integer totalVisited, Integer continuousVisited, Long totalViews,
			Integer totalComments, Integer grade) {
		this.id = id;
		this.follower = follower;
		this.following = following;
		this.totalVisited = totalVisited;
		this.continuousVisited = continuousVisited;
		this.totalViews = totalViews;
		this.totalComments = totalComments;
		this.grade = grade;
	}
	
	public Long getId() {
		return id;
	}
	
	public Integer getFollower() {
		return follower;
	}
	
	public Integer getFollowing() {
		return following;
	}
	
	public Integer getTotalVisited() {
		return totalVisited;
	}
	
	public Integer getContinuousVisited() {
		return continuousVisited;
	}
	
	public Long getTotalViews() {
		return totalViews;
	}
	
	public Integer getTotalComments() {
		return totalComments;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setFollower(Integer follower) {
		this.follower = follower;
	}

	public void setFollowing(Integer following) {
		this.following = following;
	}

	public void setTotalVisited(Integer totalVisited) {
		this.totalVisited = totalVisited;
	}

	public void setContinuousVisited(Integer continuousVisited) {
		this.continuousVisited = continuousVisited;
	}

	public void setTotalViews(Long totalViews) {
		this.totalViews = totalViews;
	}

	public void setTotalComments(Integer totalComments) {
		this.totalComments = totalComments;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
}
