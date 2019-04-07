package com.nju.vo;

import lombok.Data;

@Data
public class AdminVO {
    Long id;
    /**
     * 姓名
     */
    String name;
    /**
     * 密码
     */
    String password;
    /**
     * Yummy总财务
     */
    Double amount;
}
