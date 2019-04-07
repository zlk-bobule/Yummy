package com.nju.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResDiscountVO {
    /**
     *优惠id
     */
    private Long id;
    /**
     * 餐厅id
     */
    private Long resId;
    /**
     * 日期
     */
    private String date;
    /**
     * 满减
     */
    private List fullReduction;
    /**
     * 本店新用户立减
     */
    private Double newUserInResReduction;

    public ResDiscountVO() {
    }

    public ResDiscountVO(Long id, Long resId, String date, List fullReduction, Double newUserInResReduction) {
        this.id = id;
        this.resId = resId;
        this.date = date;
        this.fullReduction = fullReduction;
        this.newUserInResReduction = newUserInResReduction;
    }
}
