package com.nju.controller;

import com.nju.enums.ResultMessage;
import com.nju.model.FinanceLog;
import com.nju.model.Restaurant;
import com.nju.service.AdminService;
import com.nju.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录
     * @param name
     * @param password
     * @return
     */
    @GetMapping(value = "/login")
    public ResultVO login(@RequestParam("name") String name, @RequestParam("password") String password){
        ResultMessage resultMessage = adminService.login(name,password);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 得到餐厅类型的统计信息
     * @return
     */
    @GetMapping(value = "/getResTypeList")
    public ResultVO getResTypeList(){
        List<Restaurant> restaurants = adminService.getAllRes();
        Map<String,Integer> map = new LinkedHashMap<>();
        for(Restaurant restaurant : restaurants){
            String type = restaurant.getType();
            if(map.containsKey(type)){
                Integer number = map.get(type);
                number++;
                map.put(type,number);
            }else{
                map.put(type,1);
            }
        }
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),map);
    }

    /**
     * 统计一段时间新注册的用户
     * @param startDate
     * @param interval
     * @return
     */
    @GetMapping(value = "/user/tendency")
    public ResultVO getUserTendency(@RequestParam("startDate") String startDate,
                                    @RequestParam("interval") Integer interval){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = simpleDateFormat.format(date);

        Map<String,Integer> map = new LinkedHashMap<>();
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, sdf);
        LocalDate end = LocalDate.parse(endDate, sdf);
        while(start.isEqual(end) || start.isBefore(end)){
            Integer amount = adminService.countNewUserByDates(start,start.plusDays(interval-1));
            map.put(start.format(sdf), amount);
            start = start.plusDays(interval);
        }
        return new ResultVO(ResultMessage.SUCCESS.getCode(), ResultMessage.SUCCESS.getValue(), map);
    }

    @GetMapping(value = "/YummyFinance")
    public ResultVO getFinanceTendency(){
        List<FinanceLog> financeLogs = adminService.getAllFinanceLog();
        Map<String,Double> map = new LinkedHashMap<>();
        for(FinanceLog financeLog : financeLogs){
            map.put(financeLog.getDate(),financeLog.getFinance());
        }
        return new ResultVO(ResultMessage.SUCCESS.getCode(), ResultMessage.SUCCESS.getValue(), map);
    }
}
