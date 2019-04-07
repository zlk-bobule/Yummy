package com.nju.vo;

import com.nju.enums.UserLevel;
import com.nju.enums.UserState;
import com.nju.service.UserService;
import lombok.Data;

@Data
public class UserVO {
    /**
     * 用户编号
     */
    Long id;
    /**
     * 邮箱
     */
    String mail;
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
     * 账户状态
     */
    private UserState userState;
    /**
     * 累计消费总额
     */
    private Double totalPay;
    /**
     * 注册日期
     */
    private String date;

    public UserVO() {
    }

//    public UserVO(Long id, String mail) {
//        this.id = id;
//        this.mail = mail;
//    }

    public UserVO(Long id, String mail, String name, String avatar, UserLevel userLevel,UserState userState,Double totalPay,String date) {
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.avatar = avatar;
        this.userLevel = userLevel;
        this.userState = userState;
        this.totalPay = totalPay;
        this.date = date;
    }
}
