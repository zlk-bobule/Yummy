package com.nju.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 管理员
 */
@Getter
@Setter
@Entity
public class Admin extends PO implements Serializable {
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
