package com.nju.enums;

import lombok.Getter;

@Getter
public enum ResState {
    EXAMINESIGN("审批注册中",0),PASS("通过",1),EXAMINEMODIFY("审批修改中",2),NOTPASS("审核不通过",3);
    public String value;

    private Integer code;

    ResState(String value, Integer code){
        this.value=value;
        this.code = code;
    }

    public String toString(){
        return value;
    }
}
