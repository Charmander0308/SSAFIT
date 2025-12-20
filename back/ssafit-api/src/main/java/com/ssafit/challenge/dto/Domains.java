package com.ssafit.challenge.dto;

public enum Domains {
	LOGIN("member", new String[] {"total_visited, continuous_visited"},"id"),
	COMMUNITY("community", new String[] {"communityCnt"},"memberId"),
	LIKED("liked", new String[] {"likedCnt"},"memberId"),
	COMMENT("comment", new String[] {"commentCnt"},"memberId");
	
	//챌린지 점검 대상 테이블
	private final String domain;
	//챌린지 점검 대상
	private final String[] target;
	//챌린지 점검용 id칼럼명
	private final String idColumn;
	
	private Domains(String domain, String[] target, String idColumn) {
		this.domain = domain;
		this.target = target;
		this.idColumn = idColumn;
	}

	public String getDomain() {
		return domain;
	}

	public String[] getTarget() {
		return target;
	}

	public String getIdColumn() {
		return idColumn;
	}

}
