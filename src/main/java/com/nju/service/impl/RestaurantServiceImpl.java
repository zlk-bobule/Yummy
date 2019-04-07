package com.nju.service.impl;

import com.nju.dao.ResDiscountDao;
import com.nju.dao.RestaurantDao;
import com.nju.enums.ResState;
import com.nju.enums.ResultMessage;
import com.nju.model.ResDiscount;
import com.nju.model.Restaurant;
import com.nju.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantDao restaurantDao;
    private ResDiscountDao resDiscountDao;

    @Autowired
    private RestaurantServiceImpl(RestaurantDao restaurantDao, ResDiscountDao resDiscountDao){
        this.restaurantDao = restaurantDao;
        this.resDiscountDao = resDiscountDao;
    }

    @Override
    public ResultMessage register(Restaurant restaurant) {
        Restaurant restaurant1 = restaurantDao.save(restaurant);
        if(restaurant1==null){
            return ResultMessage.FAILED;
        }else{
            return ResultMessage.SUCCESS;
        }
    }

    @Override
    public Restaurant findResById(Long resId) {
        return restaurantDao.findOne(resId);
    }

    @Override
    public Restaurant modifyResInfo(Restaurant restaurant) {
        return restaurantDao.saveAndFlush(restaurant);
    }

    @Override
    public List<Restaurant> getResInfosByType(String type) {
        if(type.equals("全部")){
            return restaurantDao.findAll();
        }else{
            return restaurantDao.findByType(type);
        }
    }

    @Override
    public ResultMessage updateFrontPic(long resId, String frontPic) {
        Restaurant restaurant = restaurantDao.findOne(resId);
        restaurant.setFrontPic(frontPic);
        Restaurant restaurant1 = restaurantDao.save(restaurant);
        if(restaurant1 == null){
            return ResultMessage.NOT_EXIST;
        }else{
            return ResultMessage.SUCCESS;
        }
    }

    @Override
    public ResultMessage updateShopPic(long resId, String shopPic) {
        Restaurant restaurant = restaurantDao.findOne(resId);
        restaurant.setShopPic(shopPic);
        Restaurant restaurant1 = restaurantDao.save(restaurant);
        if(restaurant1 == null){
            return ResultMessage.NOT_EXIST;
        }else{
            return ResultMessage.SUCCESS;
        }
    }

    @Override
    public ResultMessage updataLogo(long resId, String logo) {
        Restaurant restaurant = restaurantDao.findOne(resId);
        restaurant.setLogo(logo);
        Restaurant restaurant1 = restaurantDao.save(restaurant);
        if(restaurant1 == null){
            return ResultMessage.NOT_EXIST;
        }else{
            return ResultMessage.SUCCESS;
        }
    }

    @Override
    public ResultMessage login(String identifyCode, String password) {
        List<Restaurant> restaurants = restaurantDao.findByIdentifyCodeAndResState(identifyCode,ResState.PASS);
        if(restaurants.size()==0){
            return ResultMessage.NOT_EXIST;
        }else{
            String password1 = restaurants.get(0).getPassword();
            if(password1.equals(password)){
                return ResultMessage.SUCCESS;
            }else{
                return ResultMessage.FAILED;
            }
        }
    }

    @Override
    public Restaurant findResByIdentifyCode(String identifyCode) {
        List<Restaurant> restaurants = restaurantDao.findByIdentifyCode(identifyCode);
        if(restaurants.size()!=0){
            return restaurants.get(0);
        }else{
            return null;
        }
    }

    @Override
    public ResDiscount getResDiscountByResId(Long resId) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(currentTime);
        List<ResDiscount> resDiscounts = resDiscountDao.findByResIdAndDate(resId, currentDate);
        if(resDiscounts.size()>0){
            return resDiscounts.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<Restaurant> getExaminedSignRes() {
        return restaurantDao.findByResState(ResState.EXAMINESIGN);
    }

    @Override
    public List<Restaurant> getExaminedModifyRes() {
        return restaurantDao.findByResState(ResState.EXAMINEMODIFY);
    }

    @Override
    public ResultMessage pass(Long resId) {
        Restaurant restaurant = restaurantDao.findOne(resId);
        restaurant.setResState(ResState.PASS);
        Restaurant restaurant1 = restaurantDao.save(restaurant);
        if(restaurant1 != null){
//            sendMail(restaurant1.getEmail(),"您注册的餐厅已经通过经理的审核，可以登录了！");
            return  ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage noPass(Long resId) {
        Restaurant restaurant = restaurantDao.findOne(resId);
        restaurant.setResState(ResState.NOTPASS);
        Restaurant restaurant1 = restaurantDao.save(restaurant);
        if(restaurant1 != null){
//            sendMail(restaurant1.getEmail(),"您注册的餐厅审核不通过，请重新注册");
            return  ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }

    /**
     * 发送邮件
     * @param mail
     * @param text
     */
//    private void sendMail(String mail,String text){
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost("smtp.163.com");
//        mailSender.setUsername("zlkbszlk@163.com");
//        mailSender.setPassword("zlk199858");
//        mailSender.setPort(25);
//
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.163.com");
//        props.put("mail.smtp.port", 25);
//        //设置协议
//        props.put("mail.transport.protocol", "smtp");
//        // 发件人的账号
//        props.put("mail.user", "zlkbszlk@163.com");
//        // 访问SMTP服务时需要提供的密码
//        props.put("mail.password", "zlk199858");
//
//        SimpleMailMessage mailMessage=new SimpleMailMessage();
//        System.out.println(mail);
//        mailMessage.setTo(mail);
//        mailMessage.setFrom("zlkbszlk@163.com");
//        mailMessage.setSubject("Yummy送餐系统审核通过通知");
//        mailMessage.setText(text);
//        try{
//            mailSender.send(mailMessage);
//            System.out.println("发送邮件成功");
//        }catch (Exception e){
//            System.out.println("发送邮件失败");
//        }
//    }
}
