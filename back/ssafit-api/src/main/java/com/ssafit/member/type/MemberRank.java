package com.ssafit.member.type;

public enum MemberRank {
	BASIC(0, "NONE", "bg-secondary"),
	BRONZE(1, "BRONZE", "bg-bronze"),
	SILVER(2, "SILVER", "bg-silver"),
	GOLD(3, "GOLD", "bg-gold"),
	PLATINUM(4, "PLATINUM", "bg-platinum"),
	DIAMOND(5, "DIAMOND", "bg-diamond"),
	RUBY(6, "RUBY", "bg-ruby"),
	
	ADMIN(99, "ADMIN", "bg-dark");
	
	private final int code;
	private final String label;
	private final String style;
	
	MemberRank(int code, String label, String style) {
		this.code = code;
		this.label = label;
		this.style = style;
	}
	
	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public String getStyle() {
		return style;
	}
	
	public static MemberRank of(int code) {
		for(MemberRank rank: values()) {
			if(rank.code == code) {
				return rank;
			}
		}
		return BASIC;
	}
}
