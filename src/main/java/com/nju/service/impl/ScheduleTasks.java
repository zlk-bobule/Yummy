package com.nju.service.impl;

import com.nju.dao.AdminDao;
import com.nju.dao.FinanceLogDao;
import com.nju.model.Admin;
import com.nju.model.FinanceLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 定时任务
 */
@Component
public class ScheduleTasks {
    private Logger logger = LoggerFactory.getLogger(ScheduleTasks.class);
    private Integer isRecordYummyFinanceount = 1;

    private FinanceLogDao financeLogDao;
    private AdminDao adminDao;

    @Autowired
    public ScheduleTasks(FinanceLogDao financeLogDao, AdminDao adminDao) {
        this.financeLogDao = financeLogDao;
        this.adminDao = adminDao;
    }

    @Scheduled(cron = "0 0 0 * * *")	// 每天0点0分进行一次
    public void recordYummyFinance(){
        logger.info("第{}次执行更新签到信息任务", isRecordYummyFinanceount++);

        Admin admin = adminDao.findOne(1L);
        LocalDate today = LocalDate.now();
        LocalDate yest = today.minusDays(1);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String yestStr = yest.format(fmt);
        String todayStr = today.format(fmt);
        List<FinanceLog> financeLogs = financeLogDao.findByDate(yestStr);
        if(financeLogs.size() == 0){
            financeLogDao.save(new FinanceLog(todayStr, admin.getAmount(),admin.getAmount()));
        }else{
            FinanceLog financeLog = financeLogs.get(0);
            financeLogDao.save(new FinanceLog(todayStr, admin.getAmount()-financeLog.getAmount(), admin.getAmount()));
        }
    }
}
