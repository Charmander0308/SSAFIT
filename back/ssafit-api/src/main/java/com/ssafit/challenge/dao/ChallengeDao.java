package com.ssafit.challenge.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafit.challenge.dto.ChallengeAchievedDto;
import com.ssafit.challenge.dto.ChallengeRequestDto;
import com.ssafit.challenge.dto.ChallengeResponseDto;

@Mapper
public interface ChallengeDao {
	public List<ChallengeResponseDto> selectChallengeList(Long memberId);
	
	//이벤트 발생시 챌린지 조회
	public List<String> selectTarget();
	public Integer selectTargetCntByLogin(Map<String, Object> map);
	public Integer selectTargetCnt(Map<String, Object> map);
	
	//챌린지 등록 관련
	public Long selectAchievement(Map<String, Object> map);

	public Integer insertChallenge(ChallengeRequestDto request);
	
	//달성 챌린지 아이콘 조회
	public List<String> selectAchievedChallenges(Long memberId);
	
	//달성한 챌린지 목록 담기
	public List<ChallengeAchievedDto> findAchievedById(Long memberId);
	//전체 담기
	public List<ChallengeResponseDto> findAll();

}
