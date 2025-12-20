package com.ssafit.challenge.dto;

public class ChallengeSearchDto {
	private String table;
	private String target;
	private Integer value;
	
	public ChallengeSearchDto() {}

	public ChallengeSearchDto(String table, String target, Integer value) {
		super();
		this.table = table;
		this.target = target;
		this.value = value;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ChallengeSearchDto [table=" + table + ", target=" + target + ", value=" + value + "]";
	}
	
}
