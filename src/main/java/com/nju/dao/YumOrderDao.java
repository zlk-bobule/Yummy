package com.nju.dao;

import com.nju.enums.OrderState;
import com.nju.model.YumOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YumOrderDao extends JpaRepository<YumOrder, Long>, BaseRepository<YumOrder, Long> {
    List<YumOrder> findByOrderStateAndUserId(OrderState orderState, Long userId);

    List<YumOrder> findByUserId(Long userId);

    List<YumOrder> findByResIdAndOrderState(Long resId, OrderState orderState);

    List<YumOrder> findByUserIdAndResId(Long userId, Long resId);
}
