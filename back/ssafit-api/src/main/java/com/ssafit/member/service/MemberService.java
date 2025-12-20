package com.ssafit.member.service;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafit.category.type.Category;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;
import com.ssafit.member.dao.MemberDao;
import com.ssafit.member.dto.MemberStatusResponseDto;
import com.ssafit.member.dto.FollowDto;
import com.ssafit.member.dto.MemberProfileResponseDto;
import com.ssafit.member.dto.MemberSignupRequestDto;
import com.ssafit.member.dto.MemberSimpleDto;

@Service
@Transactional(readOnly = true)
public class MemberService {
	
	private final MemberDao memberDao;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public MemberService(MemberDao memberDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.memberDao = memberDao;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	//회원등록
	@Transactional
	public void regist(MemberSignupRequestDto memberRequestDto) {
		memberRequestDto.setUserPw(bCryptPasswordEncoder.encode(memberRequestDto.getUserPw()));

		int interests = Category.toBitmasking(memberRequestDto.getCategory());
		
		int result = memberDao.save(memberRequestDto, interests);
		if(result != 1)
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
	}
	
	//유저 개인정보 조회
	public MemberProfileResponseDto findProfile(Long id) {
		MemberProfileResponseDto memberResponseDto = memberDao.findProfileById(id);
		if(memberResponseDto == null) {
			throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
		}
		List<String> interests = Category.fromBitmasking(memberResponseDto.getInterestCode());
		memberResponseDto.setInterests(interests);
		
		return memberResponseDto;
	}
	
	//유저 활동정보 조회
	public MemberStatusResponseDto findStatus(Long id) {
		if(id == null) {
			throw new CustomException(ErrorCode.INVALID_REQUEST);
		}
		MemberStatusResponseDto memberResponseDto = memberDao.findById(id);
		if(memberResponseDto == null) {
			throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
		}

		return memberResponseDto;
	}
	
	//팔로워 목록 조회
	public List<MemberSimpleDto> findFollower(Long id) {
		return memberDao.findFollowerById(id);
	}
	
	//팔로잉 목록 조회
	public List<MemberSimpleDto> findFollowing(Long id) {
		return memberDao.findFollowingById(id);
	}
	
	//from이 to를 팔로우
	@Transactional
	public void follow(FollowDto followDto) {
		if(followDto == null) 
			throw new CustomException(ErrorCode.INVALID_REQUEST);
		
		if(followDto.getFromId().equals(followDto.getToId())) 
			throw new CustomException(ErrorCode.INVALID_SELF_FOLLOW);
		
		try {
			memberDao.follow(followDto);
		} catch (DuplicateKeyException e) {
			throw new CustomException(ErrorCode.ALREADY_FOLLOWING);
		}
		
		int result = memberDao.increaseFollowingCnt(followDto.getFromId());
		if(result == 0) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
		
		result = memberDao.increaseFollowerCnt(followDto.getToId());
		if(result == 0) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
		
	}
	
}
