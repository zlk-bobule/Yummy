package com.nju.controller;

import com.nju.enums.ResultMessage;
import com.nju.model.ResDiscount;
import com.nju.model.Restaurant;
import com.nju.service.RestaurantService;
import com.nju.vo.ResDiscountVO;
import com.nju.vo.RestaurantVO;
import com.nju.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping(value = "/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(value = "/sign")
    public ResultVO register(@RequestBody RestaurantVO restaurantVO){
        String identifyCode = createRandomNum(7);
        Restaurant restaurant = new Restaurant(identifyCode, restaurantVO.getResName(), restaurantVO.getTakeOutPhone(), restaurantVO.getContactName(), restaurantVO.getContactPhone(),restaurantVO.getTypes().get(0),restaurantVO.getAddress(),restaurantVO.getLng(),restaurantVO.getLat(),restaurantVO.getPassword(), restaurantVO.getInitialDeliverPrice(), restaurantVO.getDeliverFee(),restaurantVO.getEmail());
        ResultMessage resultMessage = restaurantService.register(restaurant);

        if(resultMessage == ResultMessage.SUCCESS) {
            return new ResultVO(resultMessage.getCode(),"注册成功", restaurant.getId());
        } else {
            return new ResultVO(resultMessage.getCode(),"注册失败", null);
        }
    }

    /**
     * 通过Id查找餐厅详细信息
     * @param resId
     * @return
     */
    @GetMapping(value = "/info/{resId}")
    public ResultVO findRestaurantById(@PathVariable("resId") Long resId){
        Restaurant restaurant = restaurantService.findResById(resId);
        if(restaurant!=null){
            List<String> list = new ArrayList<>();
            list.add(restaurant.getType());
            RestaurantVO restaurantVO = new RestaurantVO(restaurant.getId(),restaurant.getIdentifyCode(),restaurant.getResName(),restaurant.getTakeOutPhone(),restaurant.getContactName(),restaurant.getContactPhone(),list,restaurant.getAddress(),restaurant.getLng(),restaurant.getLat(),restaurant.getFrontPic(),restaurant.getShopPic(),restaurant.getLogo(),restaurant.getInitialDeliverPrice(),restaurant.getDeliverFee(),restaurant.getPassword(),restaurant.getResState(),restaurant.getEmail());
            return new ResultVO(ResultMessage.SUCCESS.getCode(), ResultMessage.SUCCESS.getValue(), restaurantVO);
        }
        return new ResultVO(ResultMessage.FAILED.getCode(), ResultMessage.FAILED.getValue(), null);
    }

    /**
     * 修改餐厅信息
     * @param restaurantVO
     * @return
     */
    @PostMapping(value = "/modify/{resId}")
    public ResultVO modifyResInfo(@RequestBody RestaurantVO restaurantVO){
        Restaurant restaurant = new Restaurant(restaurantVO.getId(),restaurantVO.getIdentifyCode(),restaurantVO.getResName(),restaurantVO.getTakeOutPhone(),restaurantVO.getContactName(),restaurantVO.getContactPhone(),restaurantVO.getTypes().get(0),restaurantVO.getAddress(),restaurantVO.getLng(),restaurantVO.getLat(),restaurantVO.getFrontPic(),restaurantVO.getShopPic(),restaurantVO.getLogo(),restaurantVO.getInitialDeliverPrice(),restaurantVO.getDeliverFee(),restaurantVO.getPassword(),restaurantVO.getEmail());
        Restaurant restaurant1 = restaurantService.modifyResInfo(restaurant);
        if(restaurant1!=null){
            return new ResultVO(0,"修改成功", null);
        }else{
            return new ResultVO(1,"修改失败", null);
        }
    }

    @GetMapping(value = "/getInfos/{type}")
    public ResultVO getResInfosByType(@PathVariable("type") String type){
//        return restaurantService.getResInfosByType(type);
        List<Restaurant> list = restaurantService.getResInfosByType(type);
        List<RestaurantVO> listVOs = new ArrayList<>();
        for(Restaurant restaurant : list){
            listVOs.add(resToVO(restaurant));
        }
        System.out.println(listVOs.size());
        return new ResultVO(0,"修改成功",listVOs);
    }

    /**
     * 登录
     * @param identifyCode
     * @param password
     * @return
     */
    @GetMapping(value = "/login")
    public ResultVO login(@RequestParam("identifyCode") String identifyCode,@RequestParam("password") String password){
        ResultMessage resultMessage = restaurantService.login(identifyCode,password);
        if(resultMessage==ResultMessage.SUCCESS){
            Restaurant restaurant = restaurantService.findResByIdentifyCode(identifyCode);
            if(restaurant!=null){
                RestaurantVO restaurantVO = resToVO(restaurant);
                return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),restaurantVO);
            }else{
                return new ResultVO(ResultMessage.NOT_EXIST.getCode(),ResultMessage.NOT_EXIST.getValue(),null);
            }
        }else if(resultMessage == ResultMessage.NOT_EXIST){
            return new ResultVO(ResultMessage.NOT_EXIST.getCode(),ResultMessage.NOT_EXIST.getValue(),null);
        }else{
            return new ResultVO(ResultMessage.FAILED.getCode(),ResultMessage.FAILED.getValue(),null);
        }
    }

    /**
     * 根据餐厅id找餐厅改日发布的套餐
     * @param resId
     * @return
     */
    @GetMapping(value = "/getDiscount/{resId}")
    public ResultVO getDiscountByResId(@PathVariable("resId") Long resId){
        ResDiscount resDiscount = restaurantService.getResDiscountByResId(resId);
        if(resDiscount == null){
            return new ResultVO(ResultMessage.NOT_EXIST.getCode(),ResultMessage.NOT_EXIST.getValue(),null);
        }else{
            Map<Double,Double> map = resDiscount.getFullReduction();
            List list = new ArrayList();
            for(Map.Entry<Double,Double> entry : map.entrySet()){
                List<Double> list1 = new ArrayList();
                list1.add(entry.getKey());
                list1.add(entry.getValue());
                list.add(list1);
            }
            ResDiscountVO resDiscountVO = new ResDiscountVO(resDiscount.getId(),resDiscount.getResId(),resDiscount.getDate(),list,resDiscount.getNewUserInResReduction());
            return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),resDiscountVO);
        }
    }

    /**
     * 得到所有需要审核的餐厅
     * @return
     */
    @GetMapping(value = "/getExaminedSignRes")
    public ResultVO getExaminedSignRes(){
        List<Restaurant> restaurants = restaurantService.getExaminedSignRes();
        List<RestaurantVO> restaurantVOS = new ArrayList<>();
        for(Restaurant restaurant:restaurants){
            restaurantVOS.add(resToVO(restaurant));
        }
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),restaurantVOS);
    }

    /**
     * 得到所有需要审核修改的餐厅
     * @return
     */
    @GetMapping(value = "/getExaminedModifyRes")
    public ResultVO getExaminedModifyRes(){
        List<Restaurant> restaurants = restaurantService.getExaminedModifyRes();
        List<RestaurantVO> restaurantVOS = new ArrayList<>();
        for(Restaurant restaurant: restaurants){
            restaurantVOS.add(resToVO(restaurant));
        }
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),restaurantVOS);
    }

    /**
     * 审核通过
     * @param resId
     * @return
     */
    @GetMapping(value = "/pass/{resId}")
    public ResultVO pass(@PathVariable("resId") Long resId){
        ResultMessage resultMessage = restaurantService.pass(resId);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 审核不通过
     * @param resId
     * @return
     */
    @GetMapping(value = "/noPass/{resId}")
    public ResultVO noPass(@PathVariable("resId") Long resId){
        ResultMessage resultMessage = restaurantService.noPass(resId);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 发送审核通过邮件
     * @param mail
     * @return
     */
    @GetMapping(value = "/sendPassMail")
    public ResultVO sendPassMail(@RequestParam("mail") String mail, @RequestParam("identifyCode") String identifyCode){
        sendMail(mail,"您的餐厅信息已经通过经理的审核，可以登录了！您分配到的识别码是"+identifyCode);
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),null);
    }

    @GetMapping(value = "/sendNoPassMail")
    public ResultVO sendNoPassMail(@RequestParam("mail") String mail){
        sendMail(mail,"您的餐厅信息审核不通过，请重新注册");
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),null);
    }


    /**
     * 生成随机数
     * @param num
     * @return
     */
    public static String createRandomNum(int num){
        String randomNumStr = "";
        for(int i = 0; i < num;i ++){
            int randomNum = (int)(Math.random() * 10);
            randomNumStr += randomNum;
        }
        return randomNumStr;
    }

    /**
     * restaurant model转vo
     * @param restaurant
     * @return
     */
    private static RestaurantVO resToVO(Restaurant restaurant){
        List<String> types = new ArrayList<>();
        types.add(restaurant.getType());
        RestaurantVO restaurantVO = new RestaurantVO(restaurant.getId(),restaurant.getIdentifyCode(),restaurant.getResName(),restaurant.getTakeOutPhone(),restaurant.getContactName(),restaurant.getContactPhone(),types,restaurant.getAddress(),restaurant.getLng(),restaurant.getLat(),restaurant.getFrontPic(),restaurant.getShopPic(),restaurant.getLogo(),restaurant.getInitialDeliverPrice(),restaurant.getDeliverFee(),restaurant.getPassword(),restaurant.getResState(),restaurant.getEmail());
        return restaurantVO;
    }

    /**
     * 发送邮件
     * @param mail
     * @param text
     */
    private void sendMail(String mail,String text){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.163.com");
        mailSender.setUsername("zlkbszlk@163.com");
        mailSender.setPassword("zlk199858");
        mailSender.setPort(25);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.163.com");
        props.put("mail.smtp.port", 25);
        //设置协议
        props.put("mail.transport.protocol", "smtp");
        // 发件人的账号
        props.put("mail.user", "zlkbszlk@163.com");
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", "zlk199858");

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        System.out.println(mail);
        mailMessage.setTo(mail);
        mailMessage.setFrom("zlkbszlk@163.com");
        mailMessage.setSubject("Yummy送餐系统审核通过通知");
        mailMessage.setText(text);
        try{
            mailSender.send(mailMessage);
            System.out.println("发送邮件成功");
        }catch (Exception e){
            System.out.println("发送邮件失败");
        }
    }
}
