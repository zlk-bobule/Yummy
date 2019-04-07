package com.nju.enums;

import lombok.Getter;

@Getter
public enum ResultMessage {
    SUCCESS("成功", 0),FAILED("失败", 1),NETWORK_ERROR("网络错误", 3),NOT_EXIST("不存在", 4),EXIST("存在", 5);
    public String value;

    private Integer code;

    ResultMessage(String value, Integer code){
        this.value=value;
        this.code = code;
    }

    public String toString(){
        return value;
    }
}
