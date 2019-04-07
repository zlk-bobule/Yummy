package com.nju.enums;

import lombok.Getter;

@Getter
public enum UserState {
    VALID("有效账户",0),NOTVALID("账户已注销",2);
    public String value;

    private Integer code;

    UserState(String value, Integer code){
        this.value=value;
        this.code = code;
    }
    public String getValue() {
        return value;
    }
}
