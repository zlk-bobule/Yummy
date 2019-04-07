package com.nju.model;

import com.nju.enums.OrderState;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
@Getter
@Setter
@Entity
public class YumOrder{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_product_ids")
    @Column(name = "product_id")
    private List<Long> productIds;
    /**
     * 选中的所有套餐Id
     */
    @ElementCollection()
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "order_package_ids")
    @Column(name = "package")
    private List<Long> packageIds;
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

    public YumOrder(){}

    public YumOrder(Long userId, Long resId, List<Long> productIds, List<Long> packageIds, Double price, Double deliverFee, Double sum, Date placeTime, OrderState orderState, Long userAddressId, String remarks, String endTime) {
        this.userId = userId;
        this.resId = resId;
        this.productIds = productIds;
        this.packageIds = packageIds;
        this.price = price;
        this.deliverFee = deliverFee;
        this.sum = sum;
        this.placeTime = placeTime;
        this.orderState = orderState;
        this.userAddressId = userAddressId;
        this.remarks = remarks;
        this.endTime = "";
    }
}
