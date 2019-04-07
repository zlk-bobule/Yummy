package com.nju.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Yummy财务记录
 */
@Getter
@Setter
@Entity
public class FinanceLog extends PO implements Serializable {
    /**
     * 日期
     */
    String date;
    /**
     * 今日收入
     */
    Double finance;
    /**
     * 记录下当前yummy总收入
     */
    Double amount;

    public FinanceLog() {
    }

    public FinanceLog(String date, Double finance, Double amount) {
        this.date = date;
        this.finance = finance;
        this.amount = amount;
    }
}
