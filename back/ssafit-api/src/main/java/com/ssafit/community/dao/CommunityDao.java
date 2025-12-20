package com.ssafit.community.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafit.community.dto.CommunityRequestDto;
import com.ssafit.community.dto.CommunityResponseDto;
import com.ssafit.community.dto.CommunitySearchCondition;

@Mapper
public interface CommunityDao {
	public List<CommunityResponseDto> selectCommunityList(CommunitySearchCondition condition);
	public CommunityResponseDto selectCommunityDetail(Long communityId);
	public Integer updateViewCnt(Long communityId);
	public Integer insertCommunity(CommunityRequestDto community);
	
	//부가기능
	public void updateCommunity(CommunityRequestDto community);
	public void deleteCommunity(Long communityId);
	public Integer communityCnt(Map<String, Object> params);
}
