package com.ssafit.community.dto;

public class CommunitySearchCondition {
	//페이징?페이지네이션? 관련 필드
	private Integer page;
	private Integer pageSize;
	private Integer offSet;
	
	//검색 관련 필드
	private Integer category;
	private String word;
	private Long memberId;
	private String orderBy;
	private String orderByDir;
	
	public CommunitySearchCondition() {
		this.page = 1;
		this.pageSize = 10;
		this.orderBy = "upload_date";
		this.orderByDir = "desc";
	}

	public CommunitySearchCondition(Integer page, Integer pageSize, Integer offSet, Integer category, String word,
			Long memberId, String orderBy, String orderByDir) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.offSet = offSet;
		this.category = category;
		this.word = word;
		this.memberId = memberId;
		this.orderBy = orderBy;
		this.orderByDir = orderByDir;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getOffSet() {
		return offSet;
	}

	public void setOffSet(Integer offSet) {
		this.offSet = offSet;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderByDir() {
		return orderByDir;
	}

	public void setOrderByDir(String orderByDir) {
		this.orderByDir = orderByDir;
	}

	@Override
	public String toString() {
		return "CommunitySearchCondition [page=" + page + ", pageSize=" + pageSize + ", offSet=" + offSet
				+ ", category=" + category + ", word=" + word + ", memberId=" + memberId + ", orderBy=" + orderBy
				+ ", orderByDir=" + orderByDir + "]";
	}

}
