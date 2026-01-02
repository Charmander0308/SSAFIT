package com.ssafit.category.type;

import java.util.ArrayList;
import java.util.List;

public enum Category {
	SHOULDER(1, "어깨"),
	CHEST(2, "가슴"),
	ARM(4, "팔"),
	ABDOMEN(8, "복부"),
	BACK(16, "등"),
	HIP(32, "엉덩이"),
	THIGH(64, "허벅지"),
	CALF(128, "종아리"),
	WHOLE_BODY(256, "전신");
	
	private final int code;
	private final String label;
	
	Category(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}
	
	// view에서 db로 보낼 때 쓰는 메서드
	public static int toBitmasking(List<Integer> selected) {
		if(selected == null || selected.isEmpty()) 
			return 0;
		int sum = 0;
		for(int code : selected) {
			sum |= code;
		}
		return sum;
	}
	
	// db에서 view로 보낼 때 쓰는 메서드
	public static List<String> fromBitmasking(int value){
		List<String> list = new ArrayList<>();
		
		for(Category c : values()) {
			if((value & c.code) != 0) {
				list.add(c.label);
			}
		}
		return list;
	}

}
