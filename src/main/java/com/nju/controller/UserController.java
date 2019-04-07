package com.nju.controller;

import com.nju.enums.ResultMessage;
import com.nju.model.User;
import com.nju.model.UserAddress;
import com.nju.service.UserService;
import com.nju.vo.ResultVO;
import com.nju.vo.UserAddressVO;
import com.nju.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getVerCode", method = RequestMethod.GET)
    public ResultVO getVerCode(@RequestParam(value = "mail",required = true) String mail){
        String randomNum = createRandomNum(6);//随机生成6位数的验证码
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        System.out.println(mail);

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
        mailMessage.setTo(mail);
        mailMessage.setFrom("zlkbszlk@163.com");
        mailMessage.setSubject("会员注册验证码");
        mailMessage.setText(randomNum);

        try{
            System.out.println("发送邮件");
            mailSender.send(mailMessage);
            System.out.println("发送邮件成功");
//            return new ResultVO(0,"get VerCode success",randomNum);
            return new ResultVO(0,"get VerCode success",randomNum);
        }catch (Exception e){
            System.out.println("发送邮件失败");
            return new ResultVO(1,"获取验证码失败",null);
        }
//        mailSender.send(mailMessage);
//        return null;
    }

    /**
     * 添加地址
     * @param userAddressVO
     * @return
     */
    @PostMapping(value="/addUserAddress")
    public ResultVO addUserAddress(@RequestBody UserAddressVO userAddressVO){
        UserAddress userAddress = new UserAddress(userAddressVO.getUserId(),userAddressVO.getContactName(),userAddressVO.getGender(),userAddressVO.getPhone(),userAddressVO.getAddress(),userAddressVO.getLng(),userAddressVO.getLat(),userAddressVO.getHouseNumber());
        ResultMessage resultMessage = userService.addUserAddress(userAddress);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 注册
     * @param mail
     * @return
     */
    @GetMapping(value = "/sign")
    public ResultVO sign(@RequestParam("mail") String mail){
        User user = userService.sign(mail);
        UserVO userVO = new UserVO(user.getId(),user.getMail(), user.getName(),user.getAvatar(),user.getUserLevel(),user.getUserState(),user.getTotalPay(),user.getDate());
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),userVO);
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping(value = "/info/{userId}")
    public ResultVO getUserInfoByUserId(@PathVariable("userId") Long userId){
        User user = userService.findUserById(userId);
        if(user == null){
            return new ResultVO(ResultMessage.NOT_EXIST.getCode(),"获取用户信息失败",null);
        }else{
            UserVO userVO = new UserVO(user.getId(),user.getMail(),user.getName(),user.getAvatar(),user.getUserLevel(),user.getUserState(),user.getTotalPay(),user.getDate());
//            System.out.println(userVO);
            return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),userVO);
        }
    }

    /**
     * 保存姓名
     * @param userId
     * @param name
     * @return
     */
    @GetMapping(value = "/saveName")
    public ResultVO saveName(@RequestParam("id") Long userId,@RequestParam("name") String name){
        ResultMessage resultMessage = userService.saveName(userId,name);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 得到该用户所有的id
     * @param userId
     * @return
     */
    @GetMapping(value = "/getAddressList/{userId}")
    public ResultVO getAddressList(@PathVariable("userId") Long userId){
        List<UserAddress> userAddresses = userService.getUserAddress(userId);
        List<UserAddressVO> userAddressVOS = new ArrayList<>();
        for(UserAddress userAddress:userAddresses){
            UserAddressVO userAddressVO = new UserAddressVO(userAddress.getId(),userAddress.getUserId(),userAddress.getContactName(),userAddress.getGender(),userAddress.getPhone(),userAddress.getAddress(),userAddress.getLng(),userAddress.getLat(),userAddress.getHouseNumber());
            userAddressVOS.add(userAddressVO);
        }
//        System.out.println(userAddressVOS.size());
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),userAddressVOS);
    }

    /**
     * 修改用户状态
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/modifyState")
    public ResultVO modifyState(@PathVariable("id") Long id){
        ResultMessage resultMessage = userService.modifyState(id);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 通过用户id得到用户账单
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/getAccount")
    public ResultVO getAccountById(@PathVariable("id") Long id){
        Double account = userService.getAccountById(id);
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),account);
    }

    /**
     * 修改用户地址
     * @param userAddressVO
     * @return
     */
    @PostMapping(value = "/modifyAddress")
    public ResultVO modifyAddress(@RequestBody UserAddressVO userAddressVO){
//        System.out.println(userAddressVO);
        UserAddress userAddress = new UserAddress(userAddressVO.getId(),userAddressVO.getUserId(),userAddressVO.getContactName(),userAddressVO.getGender(),userAddressVO.getPhone(),userAddressVO.getAddress(),userAddressVO.getLng(),userAddressVO.getLat(),userAddressVO.getHouseNumber());
        ResultMessage resultMessage = userService.modifyUserAddress(userAddress);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 删除地址
     * @param id
     * @return
     */
    @GetMapping(value = "/deleteAddress/{id}")
    public ResultVO deleteAddress(@PathVariable("id") Long id){
        ResultMessage resultMessage = userService.deleteAddress(id);
        return new ResultVO(resultMessage.getCode(),resultMessage.getValue(),null);
    }

    /**
     * 通过id得到用户地址
     * @param id
     * @return
     */
    @GetMapping(value = "/getAddress/{id}")
    public ResultVO findAddressById(@PathVariable("id") Long id){
        UserAddress userAddress = userService.findUserAddressById(id);
        UserAddressVO userAddressVO = new UserAddressVO(userAddress.getId(),userAddress.getUserId(),userAddress.getContactName(),userAddress.getGender(),userAddress.getPhone(),userAddress.getAddress(),userAddress.getLng(),userAddress.getLat(),userAddress.getHouseNumber());
        return new ResultVO(ResultMessage.SUCCESS.getCode(),ResultMessage.SUCCESS.getValue(),userAddressVO);
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
}
