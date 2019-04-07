package com.nju.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nju.enums.OrderState;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 订单
 */
@Data
public class YumOrderVO {
    /**
     * 订单Id
     */
    private Long Id;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 餐厅Id
     */
    private Long resId;
    /**
     * 选中的所有单品Id
     */
    private List<String> productNames;
    /**
     * 选中的所有套餐Id
     */
    private List<String> packageNames;
    /**
     * 商品总价
     */
    private Double price;
    /**
     * 配送费
     */
    private Double deliverFee;
    /**
     * 总价
     */
    private Double sum;
    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date placeTime;
    /**
     * 订单状态
     */
    private OrderState orderState;
    /**
     * 选中用户地址id
     */
    private Long userAddressId;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 取消时间
     */
    private String endTime;

    public YumOrderVO(){}

    public YumOrderVO(Long id, Long userId, Long resId, List<String> productNames, List<String> packageNames, Double price, Double deliverFee, Double sum, Date placeTime, OrderState orderState, Long userAddressId, String remarks, String endTime) {
        Id = id;
        this.userId = userId;
        this.resId = resId;
        this.productNames = productNames;
        this.packageNames = packageNames;
        this.price = price;
        this.deliverFee = deliverFee;
        this.sum = sum;
        this.placeTime = placeTime;
        this.orderState = orderState;
        this.userAddressId = userAddressId;
        this.remarks = remarks;
        this.endTime = endTime;
    }
}
