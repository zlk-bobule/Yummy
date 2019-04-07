package com.nju.vo;

import com.nju.enums.Gender;
import lombok.Data;

@Data
public class UserAddressVO {
    Long id;
    /**
     * 用户编号
     */
    Long userId;
    /**
     * 联系人姓名
     */
    String contactName;
    /**
     * 性别
     */
    Gender gender;
    /**
     * 联系人电话
     */
    String phone;
    /**
     * 地址
     */
    String address;
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
    String houseNumber;

    public UserAddressVO(){}

    public UserAddressVO(Long id, Long userId, String contactName, Gender gender, String phone, String address, String lng, String lat, String houseNumber) {
        this.id = id;
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
