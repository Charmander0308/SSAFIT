package com.ssafit.member.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafit.member.dto.FollowDto;
import com.ssafit.member.dto.MemberAuthDto;
import com.ssafit.member.dto.MemberProfileResponseDto;
import com.ssafit.member.dto.MemberStatusResponseDto;
import com.ssafit.member.dto.MemberSignupRequestDto;
import com.ssafit.member.dto.MemberSimpleDto;

@Mapper
public interface MemberDao {
	//basic login
	Optional<MemberAuthDto> findByUserId(String userId);
	//oauth login
	Optional<MemberAuthDto> findByProvider(@Param("provider") String provider, @Param("providerId") String providerId);
	//basic signup
	int save(@Param("member") MemberSignupRequestDto memberRequestDto, @Param("interests") int interests);
	//update refreshToken
	void updateRefreshToken(@Param("id") Long id, @Param("refreshToken") String refreshToken);
	//회원 개인정보 조회
	MemberProfileResponseDto findProfileById(Long id);
	//회원 활동정보 조회
	MemberStatusResponseDto findById(Long id);
	
	//해당 회원의 팔로워 목록 조회
	List<MemberSimpleDto> findFollowerById(Long id);
	//해당 회원의 팔로잉 목록 조회
	List<MemberSimpleDto> findFollowingById(Long id);
	//팔로우 추가하기
	int follow(FollowDto followDto);
	//from의 팔로잉 수 1 추가
	int increaseFollowingCnt(Long id);
	//to의 팔로워 수 1 추가
	int increaseFollowerCnt(Long id);

}
