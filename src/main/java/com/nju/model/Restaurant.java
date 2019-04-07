package com.nju.model;

import com.nju.enums.ResState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Restaurant extends PO implements Serializable {
    /**
     * 识别码
     */
    private String identifyCode;
    /**
     * 门店名称
     */
    private String resName;
    /**
     * 外卖电话
     */
    private String takeOutPhone;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人电话
     */
    private String contactPhone;
    /**
     * 门店分类
     */
    private String type;
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
     * 门脸照
     */
    private String frontPic;
    /**
     * 店内照
     */
    private String shopPic;
    /**
     * 门店logo
     */
    private String logo;
    /**
     * 起送价
     */
    private Double initialDeliverPrice;
    /**
     * 配送费
     */
    private Double deliverFee;
    /**
     * 密码
     */
    private String password;
    /**
     * 餐厅状态
     */
    private ResState resState;
    /**
     * 邮箱
     */
    private String email;


    public Restaurant(){}

    public Restaurant(Long id, String identifyCode, String resName, String takeOutPhone, String contactName, String contactPhone, String type, String address, String lng, String lat, String frontPic, String shopPic, String logo ,Double initialDeliverPrice, Double deliverFee, String password,String email) {
        super(id);
        this.identifyCode = identifyCode;
        this.resName = resName;
        this.takeOutPhone = takeOutPhone;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.type = type;
        this.address = address;
        this.lng = lng;
        this.lat = lat;
        this.frontPic = frontPic;
        this.shopPic = shopPic;
        this.logo = logo;
        this.initialDeliverPrice = initialDeliverPrice;
        this.deliverFee = deliverFee;
        this.password = password;
        this.resState = ResState.EXAMINEMODIFY;
        this.email = email;
    }

    public Restaurant(String identifyCode, String resName, String takeOutPhone, String contactName, String contactPhone, String type, String address, String lng, String lat,String password, Double initialDeliverPrice, Double deliverFee,String email) {
        this.identifyCode = identifyCode;
        this.resName = resName;
        this.takeOutPhone = takeOutPhone;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.type = type;
        this.address = address;
        this.password = password;
        this.lng = lng;
        this.lat = lat;
        this.frontPic = "";
        this.shopPic = "";
        this.logo = "";
        this.initialDeliverPrice = initialDeliverPrice;
        this.deliverFee = deliverFee;
        this.resState = ResState.EXAMINESIGN;
        this.email = email;
    }
}
