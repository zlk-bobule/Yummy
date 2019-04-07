package com.nju.model;

import com.nju.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class UserAddress  extends PO implements Serializable {
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 性别
     */
    private Gender gender;
    /**
     * 联系人电话
     */
    private String phone;
    /**
     * 地址
     */
    private String address;
    /**
     * 经度
     */
    private String lng;
    /**
     * 纬度
     */
    private String lat;
    /**
     * 门牌号
     */
    private String houseNumber;

    public UserAddress(){}

    public UserAddress(Long userId, String contactName, Gender gender, String phone, String address, String lng, String lat, String houseNumber) {
        this.userId = userId;
        this.contactName = contactName;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.lng = lng;
        this.lat = lat;
        this.houseNumber = houseNumber;
    }

    public UserAddress(Long id, Long userId, String contactName, Gender gender, String phone, String address, String lng, String lat, String houseNumber) {
        super(id);
        this.userId = userId;
        this.contactName = contactName;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.lng = lng;
        this.lat = lat;
        this.houseNumber = houseNumber;
    }
}
