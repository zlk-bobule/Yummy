package com.nju.service.impl;

import com.nju.dao.AdminDao;
import com.nju.dao.FinanceLogDao;
import com.nju.dao.RestaurantDao;
import com.nju.dao.UserDao;
import com.nju.enums.ResState;
import com.nju.enums.ResultMessage;
import com.nju.model.Admin;
import com.nju.model.FinanceLog;
import com.nju.model.Restaurant;
import com.nju.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private AdminDao adminDao;
    private RestaurantDao restaurantDao;
    private UserDao userDao;
    private FinanceLogDao financeLogDao;

    @Autowired
    public AdminServiceImpl(AdminDao adminDao,RestaurantDao restaurantDao,UserDao userDao,FinanceLogDao financeLogDao) {
        this.adminDao = adminDao;
        this.restaurantDao = restaurantDao;
        this.userDao = userDao;
        this.financeLogDao = financeLogDao;
    }

    @Override
    public ResultMessage login(String name, String password) {
        Admin admin = adminDao.findOne(1L);
        if(admin.getName().equals(name)&&admin.getPassword().equals(password)){
            return ResultMessage.SUCCESS;
        }else {
            return ResultMessage.FAILED;
        }
    }

    @Override
    public List<Restaurant> getAllRes() {
        return restaurantDao.findByResState(ResState.PASS);
    }

    @Override
    public Integer countNewUserByDates(LocalDate startDate, LocalDate endDate) {
        Integer amount = 0;
        while(startDate.isEqual(endDate) || startDate.isBefore(endDate)){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateStr = startDate.format(fmt);
            amount += userDao.findByDate(dateStr).size();
            startDate = startDate.plusDays(1);
        }
        return amount;
    }

    @Override
    public List<FinanceLog> getAllFinanceLog() {
        return financeLogDao.findAll();
    }

}
