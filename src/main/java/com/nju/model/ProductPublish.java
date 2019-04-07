package com.nju.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 单品发布
 */
@Getter
@Setter
@Entity
public class ProductPublish extends PO implements Serializable {
    /**
     * 发布单品的餐厅Id
     */
    private Long resId;
    /**
     * 名字
     */
    private String name;
    /**
     * 价格
     */
    private Double price;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 描述
     */
    private String description;
    /**
     * 单品图片
     */
    private String image;
    /**
     * 发布日期
     */
    private String date;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    public ProductPublish(){}

    public ProductPublish(Long resId, String name, Double price, Integer number, String description, String image, String date, String startTime, String endTime) {
        this.resId = resId;
        this.name = name;
        this.price = price;
        this.number = number;
        this.description = description;
        this.image = image;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
