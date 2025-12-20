package com.ssafit.challenge.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafit.challenge.dao.ChallengeDao;
import com.ssafit.challenge.dto.ChallengeEvent;
import com.ssafit.challenge.dto.ChallengeRequestDto;
import com.ssafit.challenge.dto.ChallengeResponseDto;


@Service
@Transactional
public class ChallengeService {
	private final ChallengeDao challengeDao;
	public ChallengeService(ChallengeDao challengeDao) {
		this.challengeDao = challengeDao;
	}
	
	//챌린지 목록 조회 및 반환
	public List<ChallengeResponseDto> getChellengeList(Long memberId) {
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
	public void checkChallenge(Map<String, Object> map) {
		Long id = challengeDao.selectAchievement(map);
		if(id != null) writeChallenge(id, (Long) map.get("memberId"));
	}
	
	//챌린지 작성하는 메서드
	public boolean writeChallenge(Long challengeId, Long memberId) {
		try {
			Integer result = challengeDao.insertChallenge(new ChallengeRequestDto(challengeId, memberId));
			return result==1?true:false;
		} catch (DuplicateKeyException e) {
			return false;
		}
	}

}
