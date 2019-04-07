package com.nju.enums;

/**
 * 查询数据库的方式
 */
public enum QueryMode {
	FULL("完全匹配"), FUZZY("模糊匹配"), RANGE("范围查询"), OR("并集查询");

	String value;

	QueryMode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
