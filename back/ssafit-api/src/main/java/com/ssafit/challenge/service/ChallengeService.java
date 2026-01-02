package com.ssafit.challenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafit.challenge.dao.ChallengeDao;
import com.ssafit.challenge.dto.ChallengeAchievedDto;
import com.ssafit.challenge.dto.ChallengeEvent;
import com.ssafit.challenge.dto.ChallengeRequestDto;
import com.ssafit.challenge.dto.ChallengeResponseDto;
import com.ssafit.challenge.dto.ChallengeUserDto;
import com.ssafit.challenge.dto.ChallengingDto;
import com.ssafit.comment.dao.CommentDao;
import com.ssafit.community.dao.CommunityDao;
import com.ssafit.community.dao.LikedDao;
import com.ssafit.global.exception.CustomException;
import com.ssafit.global.exception.ErrorCode;
import com.ssafit.member.dao.MemberDao;
import com.ssafit.member.service.MemberService;


@Service
@Transactional
public class ChallengeService {
	private final ChallengeDao challengeDao;
	private final MemberDao memberDao;
	private final CommunityDao communityDao;
	private final LikedDao likedDao;
	private final CommentDao commentDao;
	
	public ChallengeService(ChallengeDao challengeDao, MemberDao memberDao, CommunityDao communityDao,
			LikedDao likedDao, CommentDao commentDao) {
		this.challengeDao = challengeDao;
		this.memberDao = memberDao;
		this.communityDao = communityDao;
		this.likedDao = likedDao;
		this.commentDao = commentDao;
	}

	//챌린지 목록 조회 및 반환
	public List<ChallengeResponseDto> getChallengeList(Long memberId) {
		return challengeDao.selectChallengeList(memberId);
	}
	
	//달성한 챌린지 아이콘 반환 메서드 추가 필요
	public List<String> achievedChallenges(Long memberId){
		return challengeDao.selectAchievedChallenges(memberId);
	}
	
	//이벤트 전달 도메인에 따라 나뉨
	//타겟(도전과제 달성 대상) 목록을 전부 가져옴 -> 도전과제 별 사용자의 현재 값 조회 -> 달성 챌린지 확인
	@EventListener
	public void challengeEvent(ChallengeEvent event) {
		String domain = event.getDomain();
		Map<String, Object> map = new HashMap();
		map.put("memberId", event.getMemberId());
		
		List<String> targetList = challengeDao.selectTarget();
		
		for(String target : targetList) {
			map.put("target", target);
			try {
				Integer value = switch(domain) {
				case "member" -> challengeDao.selectTargetCntByLogin(map);
				default -> challengeDao.selectTargetCnt(map);
				};
				map.put("value", value);
			} catch(Exception e) {
				continue;
			}
			checkChallenge(map);
		}
	}
	
	//사용자가 달성한 챌린지가 있는지 확인하는 메서드
	private void checkChallenge(Map<String, Object> map) {
		Long id = challengeDao.selectAchievement(map);
		if(id != null) writeChallenge(id, (Long) map.get("memberId"));
	}
	
	//챌린지 작성하는 메서드
	private boolean writeChallenge(Long challengeId, Long memberId) {
		try {
			Integer result = challengeDao.insertChallenge(new ChallengeRequestDto(challengeId, memberId));
			return result==1?true:false;
		} catch (DuplicateKeyException e) {
			return false;
		}
	}
	
	//회원의 도전과제현황 조회
	public ChallengeUserDto userChallengeInfo(Long id) {
		List<ChallengeResponseDto> list = challengeDao.findAll();
		if(list == null) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		//달성한 도전목록
		List<ChallengeAchievedDto> achievedList = challengeDao.findAchievedById(id);
		List<Long> achievedId = new ArrayList<>();
		for(ChallengeAchievedDto c : achievedList) {
			achievedId.add(c.getId());
		}
		//도전중인 목록
		List<ChallengingDto> challengingList = new ArrayList<>();
		for(ChallengeResponseDto c : list) {
			if(!achievedId.contains(c.getId())) {
				int userRate = getUserRate(id, c.getChallengeTarget());
				int per = (userRate * 100)/c.getChallengeGoal();
				if(per > 100) per = 100;
				challengingList.add(new ChallengingDto(
						c.getId(), 
						c.getChallengeName(), 
						c.getChallengeInfo(), 
						c.getChallengeIconDirectory(), 
						c.getChallengeTarget(), 
						c.getChallengeGoal(), 
						userRate, 
						per
						));
			}
		}
		return new ChallengeUserDto(achievedList, challengingList);
	}

	private int getUserRate(Long id, String challengeTarget) {
		int result = 0;
		switch (challengeTarget) {
		case "continuous_visited":
			result = memberDao.findById(id).getContinuousVisited();
			break;
		case "total_visited":
			result = memberDao.findById(id).getTotalVisited();
			break;
		case "community":
			result = communityDao.findTotalPostedById(id);
			break;
		case "liked":
			result = likedDao.findTotalLikedById(id);
			break;
		case "comment":
			result = commentDao.findTotalCommentsById(id);
			break;
		}
		return result;
	}
}
