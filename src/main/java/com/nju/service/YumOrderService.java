package com.nju.service;

import com.nju.enums.ResultMessage;
import com.nju.model.ResDiscount;
import com.nju.model.YumOrder;
import org.hibernate.criterion.Order;

import java.util.List;
import java.util.Map;

public interface YumOrderService {
    /**
     * 下单
     * @param yumOrder
     * @return
     */
    List<Object> placeOrder(YumOrder yumOrder);

    /**
     * 取消订单,返回退款金额
     * @param orderId
     * @return
     */
    Double cancelOrder(Long orderId);

    /**
     * 支付订单
     * @param userId
     * @param sum
     * @return
     */
    ResultMessage pay(Long userId, Double sum, Long id);

    /**
     * 通过状态和排序方式得到订单
     * @param state
     * @param sortBy
     * @return
     */
    List<YumOrder> getOrderList(String state, String sortBy, Long userId);

    /**
     * 确认订单达到
     * @param id
     * @return
     */
    ResultMessage orderArrived(Long id);

    /**
     * 得到餐厅送餐详情
     * @param resId
     * @return
     */
    List<YumOrder> getDeliverList(Long resId);

    /**
     * 得到餐厅退订
     * @param resId
     * @return
     */
    List<YumOrder> getCancelList(Long resId);

    /**
     * 得到餐厅财务状况
     * @param resId
     * @return
     */
    Map<String,Double> getFinance(Long resId);

    /**
     * 判断用户是否为新用户
     * @param userId
     * @param resId
     * @return
     */
    ResultMessage judgePlace(Long userId, Long resId);

    /**
     * 根据订单id得到订单详情
     * @param orderId
     * @return
     */
    YumOrder getOrderInfo(Long orderId);

}
