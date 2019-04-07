package com.nju.model;

import com.nju.enums.UserLevel;
import com.nju.enums.UserState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 会员
 */
@Getter
@Setter
@Entity
@Table(catalog = "yummydatabase")
public class User extends PO implements Serializable {
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 用户名
     */
    String name;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 会员等级
     */
    private UserLevel userLevel;
    /**
     * 会员状态（有效或已注销）
     */
    private UserState userState;
    /**
     * 累计消费总额
     * 消费200可成为二级会员，消费500成为三级会员，消费800成为三级会员，消费1200成为四级会员，消费2000成为五级会员
     */
    private Double totalPay;
    /**
     * 注册日期
     */
    private String date;

    public User() {
    }

    public User(String mail) {
        this.mail = mail;
        this.name = "Yummy用户";
        this.avatar = "default_avatar.png";
        this.userLevel = UserLevel.FIRSTCLASS;
        this.userState = UserState.VALID;
        this.totalPay = 0.0;
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.date = formatter.format(currentTime);
    }

    public User(String mail, String name, String avatar, UserLevel userLevel, UserState userState, Double totalPay, String date) {
        this.mail = mail;
        this.name = name;
        this.avatar = avatar;
        this.userLevel = userLevel;
        this.userState = userState;
        this.totalPay = totalPay;
        this.date = date;
    }
}
