package com.ssafit.dashboard.dto;

import java.util.List;

import com.ssafit.member.dto.MemberStatusResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "대시보드 조회결과 DTO")
public class DashboardResponseDto {
	@Schema(description = "회원 활동정보 DTO")
	private MemberStatusResponseDto memberDto;
	@Schema(description = "달성한 업적 아이콘 경로 목록", 
			example = "[\"https://s3.ap-northeast-2.amazonaws.com/ssafit/challenge/1.jpg\", "
					+ "\"https://s3.ap-northeast-2.amazonaws.com/ssafit/challenge/2.jpg\"]")
	private List<String> achievedChallenges;
	
	public DashboardResponseDto() {
	}

	private DashboardResponseDto(Builder builder) {
		this.memberDto = builder.memberDto;
		this.achievedChallenges = builder.achievedChallenges;
	}

	public MemberStatusResponseDto getMemberDto() {
		return memberDto;
	}

	public List<String> getAchievedChallenges() {
		return achievedChallenges;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private MemberStatusResponseDto memberDto;
		private List<String> achievedChallenges;
		
		public Builder memberDto(MemberStatusResponseDto memberDto) {
			this.memberDto = memberDto;
			return this;
		}

		public Builder achievedChallenges(List<String> achievedChallenges) {
			this.achievedChallenges = achievedChallenges;
			return this;
		}
		
		public DashboardResponseDto build() {
			return new DashboardResponseDto(this);
		}
	} 
	
}
