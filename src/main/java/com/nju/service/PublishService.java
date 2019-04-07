package com.nju.service;

import com.nju.enums.ResultMessage;
import com.nju.model.PackagePublish;
import com.nju.model.ProductPublish;
import com.nju.model.ResDiscount;

import java.util.List;

public interface PublishService {
    /**
     * 发布单品
     * @return
     */
    List<Object> publishProduct(ProductPublish productPublish);

    /**
     * 根据餐厅Id和时间段找合适的单品
     * @return
     */
    List<ProductPublish> getFitProductsByResIdAndTime(Long resId, String date, String startTime, String endTime);

    /**
     * 发布套餐
     * @param packagePublish
     * @return
     */
    List<Object> publishPackage(PackagePublish packagePublish);

    /**
     * 根据餐厅Id和发布单品的名字，找到发布单品的Id
     * @param name
     * @param resId
     * @return
     */
    Long findProductIdByProductNameAndResId(String name, Long resId, String date, Integer number);

    /**
     * 根据餐厅Id和发布套餐的名字和日期，找到发布套餐的Id
     * @param name
     * @param resId
     * @param date
     * @return
     */
    Long findPackageIdByPacNameAndResIdAndDate(String name, Long resId, String date, Integer number);

    /**
     *得到今日该餐厅所有的单品发布
     * @return
     */
    List<ProductPublish> getDateFitPros(Long resId);

    /**
     * 得到搜索到的今日该餐厅所有的单品发布
     * @param resId
     * @param search
     * @return
     */
    List<ProductPublish> getDateAndSearchFitPros(Long resId, String search);

    /**
     * 得到今日该餐厅所有的套餐发布
     * @param resId
     * @return
     */
    List<PackagePublish> getDateFitPacs(Long resId);

    /**
     * 得到搜索到的今日该餐厅所有的套餐发布
     * @param resId
     * @param search
     * @return
     */
    List<PackagePublish> getDateAndSearchFitPacs(Long resId, String search);

    /**
     * 根据单品Id找到单品名
     * @param proId
     * @return
     */
    String getProNameByProId(Long proId);

    /**
     * 更新发布单品的图片
     * @param id
     * @param image
     * @return
     */
    ResultMessage updateProImage(long id, String image);

    /**
     * 更新套餐发布图片
     * @param id
     * @param image
     * @return
     */
    ResultMessage updatePacImage(long id, String image);

    /**
     * 发布优惠
     * @param resDiscount
     * @return
     */
    ResultMessage publishResDiscount(ResDiscount resDiscount);

    /**
     * 根据套餐id得到套餐名
     * @param pacId
     * @return
     */
    String getPacNameByPacId(Long pacId);

    /**
     * 通过id找发布单品
     * @param id
     * @return
     */
    ProductPublish findProductByid(Long id);

    /**
     * 通过套餐id找套餐
     * @param id
     * @return
     */
    PackagePublish findPackageById(Long id);

    ResultMessage addProdutNumber(Long proId);

    ResultMessage addPackageNumber(Long pacId);


}
