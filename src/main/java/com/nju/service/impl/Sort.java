package com.nju.service.impl;

import com.nju.model.YumOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * 排序
 */
public class Sort {
    static class SortByTime implements Comparator {
        public int compare(Object o1, Object o2){
            YumOrder yumOrder1 = (YumOrder) o1;
            YumOrder yumOrder2 = (YumOrder) o2;
            return compareTime(yumOrder1.getPlaceTime(),yumOrder2.getPlaceTime());
        }

        private int compareTime(Date time1, Date time2){
            if(time1.before(time2)){
                return 1;
            }else {
                return -1;
            }
        }
    }

    static class SortBySum implements Comparator {
        public int compare(Object o1, Object o2){
            YumOrder yumOrder1 = (YumOrder) o1;
            YumOrder yumOrder2 = (YumOrder) o2;
            return yumOrder2.getSum().compareTo(yumOrder1.getSum());
        }
    }

}
