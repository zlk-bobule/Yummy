package com.nju.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Entity
public class ResDiscount extends PO implements Serializable {
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "full_reduction")
    @MapKeyColumn(name = "full")
    @Column(name = "reduction")
    private Map<Double,Double> fullReduction;
    /**
     * 本店新用户立减
     */
    private Double newUserInResReduction;

    public ResDiscount() {
    }

    public ResDiscount(Long resId, String date, Map<Double, Double> fullReduction, Double newUserInResReduction) {
        this.resId = resId;
        this.date = date;
        this.fullReduction = fullReduction;
        this.newUserInResReduction = newUserInResReduction;
    }
}
