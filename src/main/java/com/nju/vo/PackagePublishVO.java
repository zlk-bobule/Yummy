package com.nju.vo;

import lombok.Data;

import java.util.List;

/**
 * 套餐发布
 */
@Data
public class PackagePublishVO {
    /**
     * 套餐发布Id
     */
    private Long id;
    /**
     * 发布套餐的餐厅Id
     */
    private Long resId;
    /**
     * 套餐名
     */
    private String name;
    /**
     * 套餐内的单品名
     */
    private List<String> productNames;
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

    public PackagePublishVO(){}

    public PackagePublishVO(Long id, Long resId, String name, List<String> productNames, Double price, Integer number, String description, String image, String date, String startTime, String endTime) {
        this.id = id;
        this.resId = resId;
        this.name = name;
        this.productNames = productNames;
        this.price = price;
        this.number = number;
        this.description = description;
        this.image = image;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
