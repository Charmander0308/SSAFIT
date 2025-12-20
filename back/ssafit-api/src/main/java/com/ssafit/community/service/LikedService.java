package com.ssafit.community.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafit.challenge.dto.ChallengeEvent;
import com.ssafit.challenge.dto.Domains;
import com.ssafit.community.dao.LikedDao;
import com.ssafit.community.dto.LikedDto;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;

@Service
@Transactional
public class LikedService {
	private final LikedDao likedDao;
	private final ApplicationEventPublisher publisher;
	public LikedService(LikedDao likedDao, ApplicationEventPublisher publisher) {
		this.likedDao = likedDao;
		this.publisher = publisher;
	}
	
	//좋아요 여부에 따라 좋아요 상태 전환
	public void doLiked(LikedDto likedDto) {
		if(likedDto.getCommunityId() == null || likedDto.getMemberId() == null)
			throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
		try {
			likedDao.insertLiked(likedDto);	
		} catch(DuplicateKeyException e) {
			likedDao.deleteLiked(likedDto);
		} finally {
			ChallengeEvent event = new ChallengeEvent(Domains.LIKED.getDomain(),likedDto.getMemberId());
			publisher.publishEvent(event);
		}
	}
	
	
	//부가기능이므로 throw안함 
	public boolean likedCheck(LikedDto likedDto) {
		Integer check = likedDao.checkLiked(likedDto);
		return check == 1 ? true : false;
	}
	
	public Integer getLikedCnt(String domain, Long id) {
		Map<String, Object> params = new HashMap();
		params.put("domain", domain);
		params.put("id", id);
		Integer likedCnt = likedDao.LikedCnt(params);
		if(likedCnt == null) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		return likedCnt;
	}
	
	
	
	//추가 기능 구현
	//좋아요 목록 조회 : id종류에 따라 좋아요 목록을 반환한다.
	//사용자id 전달 -> 사용자 별 좋아요 글 목록반환
	public List<LikedDto> getLikedList(String type, Long value){
		Map<String, Object> params = new HashMap();
		params.put("type", type);
		params.put("value", value);
		return likedDao.selectLikedList(params);
	}	
}
