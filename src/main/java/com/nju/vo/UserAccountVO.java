package com.nju.vo;

import lombok.Data;

@Data
public class UserAccountVO {
    /**
     * 账户id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 余额
     */
    private Double account;
}
