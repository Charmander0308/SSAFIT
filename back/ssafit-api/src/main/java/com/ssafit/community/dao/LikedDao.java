package com.ssafit.community.dao;

import java.util.List;
import java.util.Map;

import com.ssafit.community.dto.LikedDto;

public interface LikedDao {
	//사용자의 좋아요 선택여부 확인
	public Integer checkLiked(LikedDto dto);
	//좋아요 선택
	public void insertLiked(LikedDto dto);
	//좋아요 취소
	public Integer deleteLiked(LikedDto dto);
	
	//부가 기능 구현
	//대상별로 liked 목록 추출 기능 추가(community에서 조회시 nickname을 가져오도록 하겠습니다.)
	public List<LikedDto> selectLikedList(Map<String, Object> params);
	//대상 별로 liked개수 추출 기능 추가
	public Integer LikedCnt(Map<String, Object> params);
	
	public Integer findTotalLikedById(Long id);
}
