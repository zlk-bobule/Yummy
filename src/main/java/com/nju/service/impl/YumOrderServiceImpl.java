package com.nju.service.impl;

//import com.nju.dao.YumOrderDao;
import com.nju.dao.AdminDao;
import com.nju.dao.UserAccountDao;
import com.nju.dao.UserDao;
import com.nju.dao.YumOrderDao;
import com.nju.enums.OrderState;
import com.nju.enums.ResultMessage;
import com.nju.enums.UserLevel;
import com.nju.model.*;
import com.nju.service.YumOrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class YumOrderServiceImpl implements YumOrderService {

    private YumOrderDao yumOrderDao;
    private UserAccountDao userAccountDao;
    private UserDao userDao;
    private AdminDao adminDao;

    @Autowired
    private YumOrderServiceImpl(YumOrderDao yumOrderDao, UserAccountDao userAccountDao,UserDao userDao,AdminDao adminDao){
        this.yumOrderDao = yumOrderDao;
        this.userAccountDao = userAccountDao;
        this.userDao = userDao;
        this.adminDao = adminDao;
    }

    @Override
    public List<Object> placeOrder(YumOrder yumOrder) {
        YumOrder yumOrder1 = yumOrderDao.save(yumOrder);
        List<Object> list = new ArrayList<>();
        if(yumOrder1 == null){
            list.add(ResultMessage.FAILED);
        }else{
            list.add(ResultMessage.SUCCESS);
            list.add(yumOrder1.getId());
        }
        return list;
    }

    @Override
    public Double cancelOrder(Long orderId) {
        YumOrder yumOrder = yumOrderDao.findOne(orderId);
        OrderState orderState = yumOrder.getOrderState();
        yumOrder.setOrderState(OrderState.CANCEL);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        yumOrder.setEndTime(formatter.format(currentTime));
        YumOrder yumOrder1 = yumOrderDao.save(yumOrder);
        if(yumOrder1 != null){
//            return ResultMessage.SUCCESS;
            if(orderState == OrderState.PLACE){
                return 0.0;
            }else{
                Date date = yumOrder.getPlaceTime();
                long interval = currentTime.getTime() - date.getTime();
                Double amount = 0.0;
                if(interval >= 40*60*1000){
                    BigDecimal bg = new BigDecimal(yumOrder.getSum()*60/100);
                    amount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }else{
                    double ratio = interval/(40*60*1000);
                    ratio = (100 - 40 * ratio)/100;
                    BigDecimal bg = new BigDecimal(yumOrder.getSum() * ratio);
                    amount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                List<UserAccount> userAccounts = userAccountDao.findByUserId(yumOrder1.getUserId());
                if(userAccounts.size()>0){
                    UserAccount userAccount = userAccounts.get(0);
                    Double userAmount = userAccount.getAccount();
                    userAccount.setAccount(userAmount+amount);
                    userAccountDao.save(userAccount);
                    Admin admin = adminDao.findOne(1L);
                    Double amount1 = admin.getAmount();
                    admin.setAmount(amount1-amount);
                    adminDao.save(admin);
                }
                User user = userDao.findOne(yumOrder1.getUserId());
                Double totalPay = user.getTotalPay();
                totalPay -= yumOrder.getSum();
                user.setTotalPay(totalPay);
                user.setUserLevel(getUserLevelByTotalPay(totalPay));
                userDao.save(user);
                return amount;
            }
        }else{
            return 0.0;
        }
    }

    @Override
    public ResultMessage pay(Long userId, Double sum, Long id) {
        List<UserAccount> userAccounts = userAccountDao.findByUserId(userId);
        if (userAccounts.size() == 0) {
            return ResultMessage.NOT_EXIST;
        } else {
            UserAccount userAccount = userAccounts.get(0);
            Double account = userAccount.getAccount();
            account = account - sum;
            userAccount.setAccount(account);
            UserAccount userAccount1 = userAccountDao.save(userAccount);
            //订单状态变为已支付，下单时间改为支付时间
            YumOrder yumOrder = yumOrderDao.findOne(id);
            yumOrder.setOrderState(OrderState.PAID);
            Date currentTime = new Date();
            yumOrder.setPlaceTime(currentTime);
            YumOrder yumOrder1 = yumOrderDao.save(yumOrder);
            //修改用户累计消费总额
            User user = userDao.findOne(userId);
            Double totalPay = user.getTotalPay();
            totalPay += sum;
            user.setTotalPay(totalPay);
            user.setUserLevel(getUserLevelByTotalPay(totalPay));
            userDao.save(user);
            Admin admin = adminDao.findOne(1L);
            Double amount = admin.getAmount();
            admin.setAmount(amount+sum);
            adminDao.save(admin);
            if (userAccount1 != null) {
                return ResultMessage.SUCCESS;
            } else {
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public List<YumOrder> getOrderList(String state, String sortBy, Long userId) {
        List<YumOrder> list = new ArrayList<>();
        switch (state) {
            case "ALL":
                list = yumOrderDao.findByUserId(userId);
                break;
            case "PLACE":
                list = yumOrderDao.findByOrderStateAndUserId(OrderState.PLACE,userId);
                break;
            case "PAID":
                list = yumOrderDao.findByOrderStateAndUserId(OrderState.PAID,userId);
                break;
            case "DELIVERED":
                list = yumOrderDao.findByOrderStateAndUserId(OrderState.DELIVERED,userId);
                break;
            case "CANCEL":
                list = yumOrderDao.findByOrderStateAndUserId(OrderState.CANCEL,userId);
                break;
            default:
                break;
        }
        if(sortBy.equals("时间")){
            Sort.SortByTime sortByTime = new Sort.SortByTime();
            Collections.sort(list,sortByTime);
            return list;
        }else if(sortBy.equals("金额")){
            Sort.SortBySum sortBySum = new Sort.SortBySum();
            Collections.sort(list,sortBySum);
            return list;
        }else{
            Map<Long,List<YumOrder>> map = new HashMap<>();
            for(YumOrder yumOrder:list){
                List<YumOrder> tempList = map.get(yumOrder.getResId());
                if(tempList == null){
                    tempList = new ArrayList<>();
                    tempList.add(yumOrder);
                    map.put(yumOrder.getResId(),tempList);
                } else {
                    tempList.add(yumOrder);
                }
            }
            List<YumOrder> result = new ArrayList<>();
            for(Long resId:map.keySet()){
                List<YumOrder> yumOrders = map.get(resId);
                result.addAll(yumOrders);
            }
            return result;
        }
    }

    @Override
    public ResultMessage orderArrived(Long id) {
        YumOrder yumOrder = yumOrderDao.findOne(id);
        if(yumOrder != null){
            yumOrder.setOrderState(OrderState.DELIVERED);
            yumOrderDao.save(yumOrder);
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public List<YumOrder> getDeliverList(Long resId) {
        List<YumOrder> yumOrders1 = yumOrderDao.findByResIdAndOrderState(resId,OrderState.PAID);
        List<YumOrder> yumOrders2 = yumOrderDao.findByResIdAndOrderState(resId,OrderState.DELIVERED);
        yumOrders1.addAll(yumOrders2);
        Sort.SortByTime sortByTime = new Sort.SortByTime();
        Collections.sort(yumOrders1,sortByTime);
        return yumOrders1;
    }

    @Override
    public List<YumOrder> getCancelList(Long resId) {
        List<YumOrder> yumOrders = yumOrderDao.findByResIdAndOrderState(resId,OrderState.CANCEL);
        Sort.SortByTime sortByTime = new Sort.SortByTime();
        Collections.sort(yumOrders,sortByTime);
        return yumOrders;
    }

    @Override
    public Map<String, Double> getFinance(Long resId) {
        List<YumOrder> yumOrders1 = yumOrderDao.findByResIdAndOrderState(resId,OrderState.PAID);
        List<YumOrder> yumOrders2 = yumOrderDao.findByResIdAndOrderState(resId,OrderState.DELIVERED);
        yumOrders1.addAll(yumOrders2);
        Sort.SortByTime sortByTime = new Sort.SortByTime();
        Collections.sort(yumOrders1,sortByTime);
        Map<String,Double> map = new HashMap<>();
        for(YumOrder yumOrder:yumOrders1){
            String date = getDate(yumOrder);
            if(map.containsKey(date)){
                Double amount = map.get(date);
                amount = amount + yumOrder.getSum();
                map.put(date,amount);
            }else{
                Double amount = yumOrder.getSum();
                map.put(date,amount);
            }
        }
        for(String key : map.keySet()){
            BigDecimal bg = new BigDecimal(map.get(key)*98/100);
            map.put(key,bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        return map;
    }

    @Override
    public ResultMessage judgePlace(Long userId, Long resId) {
        List<YumOrder> yumOrders = yumOrderDao.findByUserIdAndResId(userId, resId);
        if(yumOrders.size() == 0){
            return ResultMessage.FAILED;
        } else {
            return ResultMessage.SUCCESS;
        }
    }

    @Override
    public YumOrder getOrderInfo(Long orderId) {
        YumOrder yumOrder = yumOrderDao.findOne(orderId);
        return yumOrder;
    }

    /**
     * 得到订单下单的日期
     * @param yumOrder
     * @return
     */
    private String getDate(YumOrder yumOrder){
        Date date = yumOrder.getPlaceTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @param totalPay
     * @return
     */
    private UserLevel getUserLevelByTotalPay(Double totalPay){
        if(totalPay < 200){
            return UserLevel.FIRSTCLASS;
        }else if(totalPay < 500){
            return UserLevel.SECONDCLASS;
        }else if(totalPay <800){
            return UserLevel.THREECLASS;
        }else if(totalPay <1200){
            return UserLevel.FOURCLASS;
        }else{
            return UserLevel.FIVECLASS;
        }
    }
}
