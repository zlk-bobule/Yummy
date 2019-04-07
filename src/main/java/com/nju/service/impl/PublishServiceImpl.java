package com.nju.service.impl;

import com.nju.dao.PackagePublishDao;
import com.nju.dao.ProductPublishDao;
import com.nju.dao.ResDiscountDao;
import com.nju.enums.QueryMode;
import com.nju.enums.ResultMessage;
import com.nju.model.PackagePublish;
import com.nju.model.ProductPublish;
import com.nju.model.ResDiscount;
import com.nju.service.PublishService;
import com.nju.utils.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PublishServiceImpl implements PublishService {

    private ProductPublishDao productPublishDao;
    private PackagePublishDao packagePublishDao;
    private ResDiscountDao resDiscountDao;

    @Autowired
    private PublishServiceImpl(ProductPublishDao productPublishDao, PackagePublishDao packagePublishDao, ResDiscountDao resDiscountDao){
        this.productPublishDao = productPublishDao;
        this.packagePublishDao = packagePublishDao;
        this.resDiscountDao = resDiscountDao;
    }

    @Override
    public List<Object> publishProduct(ProductPublish productPublish) {
        ProductPublish productPublish1 = productPublishDao.save(productPublish);
        List<Object> list = new ArrayList<>();
        if(productPublish1==null){
            list.add(ResultMessage.FAILED);
            list.add(null);
        }else{
            list.add(ResultMessage.SUCCESS);
            list.add(productPublish1.getId());
        }
        return list;
    }

    @Override
    public List<ProductPublish> getFitProductsByResIdAndTime(Long resId, String date, String startTime, String endTime) {
        List<ProductPublish> productPublishes = productPublishDao.findByResIdAndDate(resId, date);
        List<ProductPublish> resultList = new ArrayList<>();
        for(ProductPublish productPublish : productPublishes){
            if(compareTime(productPublish.getStartTime(),startTime)<0 && compareTime(endTime,productPublish.getEndTime())<0){
                resultList.add(productPublish);
            }
        }
        return resultList;
    }

    @Override
    public List<Object> publishPackage(PackagePublish packagePublish) {
        PackagePublish packagePublish1 = packagePublishDao.save(packagePublish);
        List<Object> list = new ArrayList<>();
        if(packagePublish1==null){
            list.add(ResultMessage.FAILED);
        }else{
            list.add(ResultMessage.SUCCESS);
            list.add(packagePublish1.getId());
        }
        return list;
    }

    @Override
    public Long findProductIdByProductNameAndResId(String name, Long resId, String date, Integer number) {
        List<ProductPublish> productPublishes = productPublishDao.findByNameAndResIdAndDate(name, resId, date);
        if(productPublishes.size()>0) {
            ProductPublish productPublish = productPublishes.get(0);
            Integer newNumber = productPublish.getNumber();
            if(newNumber < number){
                return -1L;
            }else {
                productPublish.setNumber(newNumber - number);
                ProductPublish productPublish1 = productPublishDao.save(productPublish);
                return productPublish1.getId();
            }
        }else{
            return null;
        }
    }

    @Override
    public Long findPackageIdByPacNameAndResIdAndDate(String name, Long resId, String date, Integer number) {
        List<PackagePublish> packagePublishes = packagePublishDao.findByNameAndResIdAndDate(name, resId, date);
        if(packagePublishes.size()>0){
            PackagePublish packagePublish = packagePublishes.get(0);
            Integer newNumber = packagePublish.getNumber();
            if(newNumber < number){
                return -1L;
            }else{
                packagePublish.setNumber(newNumber-number);
                PackagePublish packagePublish1 = packagePublishDao.save(packagePublish);
                return packagePublish1.getId();
            }
        }else{
            return null;
        }
    }

    @Override
    public List<ProductPublish> getDateFitPros(Long resId) {
        String dateString = getCurrentDate();
        List<ProductPublish> pros = productPublishDao.findByResIdAndDate(resId,dateString);
        return pros;
    }

    @Override
    public List<ProductPublish> getDateAndSearchFitPros(Long resId, String search) {
        return productPublishDao.multiQuery(getDateAndSearchFitsCriterions(resId,search));
    }

    @Override
    public List<PackagePublish> getDateFitPacs(Long resId) {
        String dateString = getCurrentDate();
        return packagePublishDao.findByResIdAndDate(resId,dateString);
    }

    @Override
    public List<PackagePublish> getDateAndSearchFitPacs(Long resId, String search) {
        return packagePublishDao.multiQuery(getDateAndSearchFitsCriterions(resId,search));
    }

    @Override
    public String getProNameByProId(Long proId) {
        ProductPublish productPublish = productPublishDao.findOne(proId);
        return productPublish.getName();
    }

    @Override
    public ResultMessage updateProImage(long id, String image) {
        ProductPublish productPublish = productPublishDao.findOne(id);
        productPublish.setImage(image);
        ProductPublish productPublish1 = productPublishDao.save(productPublish);
        if(productPublish1!=null){
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage updatePacImage(long id, String image) {
        PackagePublish packagePublish = packagePublishDao.findOne(id);
        packagePublish.setImage(image);
        PackagePublish packagePublish1 = packagePublishDao.save(packagePublish);
        if(packagePublish1!=null){
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage publishResDiscount(ResDiscount resDiscount) {
        ResDiscount resDiscount1 = resDiscountDao.save(resDiscount);
        if(resDiscount1!=null){
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }

    @Override
    public String getPacNameByPacId(Long pacId) {
        PackagePublish packagePublish = packagePublishDao.findOne(pacId);
        return packagePublish.getName();
    }

    @Override
    public ProductPublish findProductByid(Long id) {
        ProductPublish productPublish = productPublishDao.findOne(id);
        return productPublish;
    }

    @Override
    public PackagePublish findPackageById(Long id) {
        PackagePublish packagePublish = packagePublishDao.findOne(id);
        return packagePublish;
    }

    @Override
    public ResultMessage addProdutNumber(Long proId) {
        ProductPublish productPublish = productPublishDao.findOne(proId);
        if(productPublish!=null){
            Integer number = productPublish.getNumber();
            productPublish.setNumber(number+1);
            productPublishDao.save(productPublish);
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage addPackageNumber(Long pacId) {
        PackagePublish packagePublish = packagePublishDao.findOne(pacId);
        if(packagePublish != null){
            Integer number = packagePublish.getNumber();
            packagePublish.setNumber(number+1);
            packagePublishDao.save(packagePublish);
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }

    /**
     * 比较时分格式时间的大小
     * @param time1
     * @param time2
     * @return 返回0表示格式不正确，返回1表示time1>time2,返回-1表示time1<time2
     */
    private int compareTime(String time1, String time2){
        if(time1.indexOf(":")<0||time2.indexOf(":")<0){
            System.out.println("格式不正确");
            return 0;
        }else{
            String array1[] = time1.split(":");
            String array2[] = time2.split(":");
            int timeAdd1 = Integer.valueOf(array1[0])*60 + Integer.valueOf(array1[1]);
            int timeAdd2 = Integer.valueOf(array2[0])*60 + Integer.valueOf(array2[1]);
            if(timeAdd1>timeAdd2){
                return 1;
            }else{
                return -1;
            }
        }
    }

    /**
     * 得到今日的日期，格式为“yyyy-MM-dd”
     * @return
     */
    private String getCurrentDate(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentTime);
    }

    /**
     * 得到resId完全匹配，search模糊匹配的List<Criterion>
     * @param resId
     * @param search
     * @return
     */
    private List<Criterion> getDateAndSearchFitsCriterions(Long resId, String search){
        String dateString = getCurrentDate();

        ArrayList<Criterion> criteria = new ArrayList<>();
        criteria.add(new Criterion<>("date", dateString, QueryMode.FULL));
        criteria.add(new Criterion<>("resId", resId, QueryMode.FULL));
        criteria.add(new Criterion<>("name", search, QueryMode.FUZZY));
        return criteria;
    }
}
