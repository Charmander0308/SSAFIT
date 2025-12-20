package com.ssafit.comment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafit.challenge.dto.ChallengeEvent;
import com.ssafit.challenge.dto.Domains;
import com.ssafit.comment.dao.CommentDao;
import com.ssafit.comment.dto.CommentRequestDto;
import com.ssafit.comment.dto.CommentResponseDto;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;

@Service
@Transactional
public class CommentService {
	private final CommentDao commentDao;
	private final ApplicationEventPublisher publisher;
	public CommentService(CommentDao commentDao, ApplicationEventPublisher publisher) {
		this.commentDao = commentDao;
		this.publisher = publisher;
	}
	
	//댓글 목록 반환. 테이블 이름(community, member, youtube)에 따라 id종류 변경
	//다른 도메인 서비스에서 호출되는 역할
	//다른 서비스의 부가기능 역할을 하기에 에러를 throw하지 않음
	public List<CommentResponseDto> getCommentListByDomain(String domain, Long id){
		Map<String, Object> params = new HashMap<>();
		params.put("domain", domain);
		params.put("id", id);
		return commentDao.selectCommentByDomain(params);
	}
	//댓글 개수 조회. communityDatail등에서 사용
	public Integer getCommentCnt(String domain, Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put("domain", domain);
		params.put("id", id);
		return commentDao.commentCnt(params);
	}
	
	//댓글 작성 요청 처리
	public void writeComment(CommentRequestDto comment) {
		if(comment.getCommunityId() != null && comment.getYoutubeVideoId() != null)
			throw new CustomException(ErrorCode.INVALID_REQUEST);
		
		Integer result = commentDao.insertComment(comment);
		if(result != 1)
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		
		ChallengeEvent event = new ChallengeEvent(Domains.COMMENT.getDomain(),comment.getMemberId());
		publisher.publishEvent(event);
	}

	
	
	//추가 기능 구현
	//단일 댓글 조회
	public CommentResponseDto getComment(CommentRequestDto comment) {
		return commentDao.selectComment(comment);
	}
	//update / delete 추가 필요
}
