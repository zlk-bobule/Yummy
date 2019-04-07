package com.nju.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 套餐发布
 */
@Getter
@Setter
@Entity
public class PackagePublish extends PO implements Serializable {
    /**
     * 发布套餐的餐厅Id
     */
    private Long resId;
    /**
     * 套餐名
     */
    private String name;
    /**
     * 套餐内的单品Id
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_ids")
    @Column(name = "product_id")
    private List<Long> productIds;
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
     * 图片名
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

    public PackagePublish(){}

    public PackagePublish(Long resId, String name, List<Long> productIds, Double price, Integer number, String description, String image, String date, String startTime, String endTime) {
        this.resId = resId;
        this.name = name;
        this.productIds = productIds;
        this.price = price;
        this.number = number;
        this.description = description;
        this.image = image;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
