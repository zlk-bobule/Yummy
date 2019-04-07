package com.nju.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class UserAccount extends PO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 余额
     */
    private Double account;

    public UserAccount(){}

    public UserAccount(Long userId) {
        this.userId = userId;
        this.account = 0.0;
    }
}
