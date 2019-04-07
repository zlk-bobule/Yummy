package com.nju.service;

import com.nju.enums.ResultMessage;
import com.nju.model.ResDiscount;
import com.nju.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    /**
     * 注册
     * @param restaurant
     * @return
     */
    ResultMessage register(Restaurant restaurant);

    /**
     * 通过餐厅Id查找餐厅详细信息
     * @param resId
     * @return
     */
    Restaurant findResById(Long resId);

    /**
     * 修改餐厅信息
     * @param restaurant
     * @return
     */
    Restaurant modifyResInfo(Restaurant restaurant);

    /**
     * 通过类型得到餐厅信息
     * @param type
     * @return
     */
    List<Restaurant> getResInfosByType(String type);

    /**
     * 更新门脸照
     * @param resId
     * @param frontPic
     * @return
     */
    ResultMessage updateFrontPic(long resId, String frontPic);

    /**
     * 更新门脸照
     * @param resId
     * @param shopPic
     * @return
     */
    ResultMessage updateShopPic(long resId, String shopPic);

    /**
     * 更新logo
     * @param resId
     * @param logo
     * @return
     */
    ResultMessage updataLogo(long resId, String logo);

    /**
     * 通过识别码登录
     * @param identifyCode
     * @param password
     * @return
     */
    ResultMessage login(String identifyCode, String password);

    /**
     * 通过识别码找餐厅
     * @param identifyCode
     * @return
     */
    Restaurant findResByIdentifyCode(String identifyCode);

    /**
     * 通过餐厅id找餐厅改日发布的套餐
     * @param resId
     * @return
     */
    ResDiscount getResDiscountByResId(Long resId);

    /**
     * 得到需要审核的餐厅
     * @return
     */
    List<Restaurant> getExaminedSignRes();

    /**
     * 得到所有需要审核修改的餐厅
     * @return
     */
    List<Restaurant> getExaminedModifyRes();

    /**
     * 审核通过
     * @param resId
     * @return
     */
    ResultMessage pass(Long resId);

    /**
     * 审核不通过
     * @param resId
     * @return
     */
    ResultMessage noPass(Long resId);
}
