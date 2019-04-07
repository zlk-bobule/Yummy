package com.nju.enums;

import lombok.Getter;

@Getter
public enum UserLevel {
    FIRSTCLASS("一级会员",1),SECONDCLASS("二级会员",2),THREECLASS("三级会员",3),FOURCLASS("四级会员",4),FIVECLASS("五级会员",5);
    public String value;

    private Integer code;

    UserLevel(String value, Integer code){
        this.value=value;
        this.code = code;
    }
    public String getValue() {
        return value;
    }
}
