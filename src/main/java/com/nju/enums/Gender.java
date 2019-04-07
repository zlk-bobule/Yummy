package com.nju.enums;

import lombok.Getter;

/**
 * 性别
 */
@Getter
public enum Gender {
    MALE("男性",0),FEMALE("女性",1);
    public String value;

    private Integer code;

    Gender(String value, Integer code){
        this.value=value;
        this.code = code;
    }

    public String toString(){
        return value;
    }
}
