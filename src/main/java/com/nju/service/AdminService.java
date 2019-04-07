package com.nju.service;

import com.nju.enums.ResultMessage;
import com.nju.model.FinanceLog;
import com.nju.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {
    /**
     * 登录
     * @param name
     * @param password
     * @return
     */
    ResultMessage login(String name, String password);

    /**
     * 得到所有状态为‘通过‘的餐厅信息
     * @return
     */
    List<Restaurant> getAllRes();

    /**
     * 统计某段时间新注册的用户
     * @param startDate
     * @param endDate
     * @return
     */
    Integer countNewUserByDates(LocalDate startDate, LocalDate endDate);

    /**
     * 得到所有财务记录
     * @return
     */
    List<FinanceLog> getAllFinanceLog();
}
