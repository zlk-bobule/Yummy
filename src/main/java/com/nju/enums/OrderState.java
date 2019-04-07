package com.nju.enums;

import lombok.Getter;

@Getter
public enum OrderState {
    PLACE("下单未付款",0),PAID("付款后商家制作配送中",1),DELIVERED("已送达",2),CANCEL("已取消",3);
    public String value;

    private Integer code;

    OrderState(String value, Integer code){
        this.value=value;
        this.code = code;
    }

    public String toString(){
        return value;
    }
}
