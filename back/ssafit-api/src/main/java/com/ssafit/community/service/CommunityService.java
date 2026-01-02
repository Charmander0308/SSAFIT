package com.ssafit.community.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafit.challenge.dto.ChallengeEvent;
import com.ssafit.challenge.dto.Domains;
import com.ssafit.comment.service.CommentService;
import com.ssafit.community.dao.CommunityDao;
import com.ssafit.community.dto.CommunityRequestDto;
import com.ssafit.community.dto.CommunityResponseDto;
import com.ssafit.community.dto.CommunitySearchCondition;
import com.ssafit.community.dto.LikedDto;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;

@Service
@Transactional(readOnly = true)
public class CommunityService {
	private final CommunityDao communityDao;
	private final LikedService likedService;
	private final CommentService commentService;
	private final ApplicationEventPublisher publisher;
	public CommunityService(CommunityDao communityDao, LikedService likedService
			, CommentService commentService, ApplicationEventPublisher publisher) {
		this.communityDao = communityDao;
		this.likedService = likedService;
		this.commentService = commentService;
		this.publisher = publisher;
	}
	
	//게시글 목록 조회
	public List<CommunityResponseDto> getCommunityList(CommunitySearchCondition condition){
		condition.setOffSet((condition.getPage()-1)*condition.getPageSize());
		List<CommunityResponseDto> communityList = communityDao.selectCommunityList(condition);
		if(communityList == null) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		return communityList;
	}
	
	//게시글 세부 내용 조회
	@Transactional
	public CommunityResponseDto readCommunity(Long communityId, Long memberId) {
		if(communityId == null || memberId == null) {
			throw new CustomException(ErrorCode.INVALID_REQUEST);
		}
		
		communityDao.updateViewCnt(communityId);
		CommunityResponseDto community = communityDao.selectCommunityDetail(communityId);
		if(community == null) 
			throw new CustomException(ErrorCode.COMMUNITY_NOT_FOUND);
		
		community.setLiked(likedService.getLikedCnt(Domains.COMMUNITY.getDomain(), communityId));
		boolean likedCheck = likedService.likedCheck(new LikedDto(communityId, memberId));
		community.setLikedCheck(likedCheck);
		community.setComments(commentService.getCommentListByDomain(Domains.COMMUNITY.getDomain(), communityId));
		community.setCommentCnt(commentService.getCommentCnt(Domains.COMMUNITY.getDomain(), communityId));
		//부가 정보의 에러(null)는 프론트에서 처리
		return community;
	}
	
	//게시글 작성
	@Transactional
	public void writeCommunity(CommunityRequestDto communityRequestDto) {
		Integer result = communityDao.insertCommunity(communityRequestDto);
		if(result != 1) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		} 
		ChallengeEvent event = new ChallengeEvent(Domains.COMMUNITY.getDomain(),communityRequestDto.getMemberId());
		publisher.publishEvent(event);
	}
	
	
	
	
	
	//추가 기능
	//수정페이지에서 단일 게시글 조회
	public CommunityResponseDto getCommunity(Long communityId) {
		return communityDao.selectCommunityDetail(communityId);
	} 
	//게시글 수정
	@Transactional
	public void modifyCommunity(CommunityRequestDto communityRequestDto) {
		communityDao.updateCommunity(communityRequestDto);
	}
	//게시글 삭제
	@Transactional
	public void removeCommunity(Long communityId) {
		communityDao.deleteCommunity(communityId);
	}
	//게시글 갯수 조회(카테고리 뿐 아니라 여러 조건 추가 필요할 듯)
	public Integer getCommunityCnt(String category, Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put("category", category);
		params.put("value", id);
		return communityDao.communityCnt(params);
	}
}
