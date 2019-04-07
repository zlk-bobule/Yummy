package com.nju.service;

import com.nju.enums.ResultMessage;
import com.nju.model.User;
import com.nju.model.UserAddress;
import com.nju.vo.ResultVO;

import java.util.List;

public interface UserService {
    /**
     * 增加用户地址
     * @param userAddress
     * @return
     */
    ResultMessage addUserAddress(UserAddress userAddress);

    /**
     * 用户注册登录
     * @param mail
     * @return
     */
    User sign(String mail);
    /**
     * 更改头像
     * @param userId
     * @param avatar
     * @return
     */
    ResultMessage updateAvatar(long userId,String avatar);

    /**
     * 通过id找user
     * @param id
     * @return
     */
    User findUserById(Long id);

    /**
     * 保存用户名
     * @param id
     * @param name
     * @return
     */
    ResultMessage saveName(Long id, String name);

    /**
     * 根据userId找UserAddresses
     * @param userId
     * @return
     */
    List<UserAddress> getUserAddress(Long userId);

    /**
     * 修改用户状态，注销账户
     * @param id
     * @return
     */
    ResultMessage modifyState(Long id);

    /**
     * 通过用户id得到账户余额
     * @param id
     * @return
     */
    Double getAccountById(Long id);

    /**
     * 修改用户地址
     * @param userAddress
     * @return
     */
    ResultMessage modifyUserAddress(UserAddress userAddress);

    /**
     * 删除用户地址
     * @param id
     * @return
     */
    ResultMessage deleteAddress(Long id);

    /**
     * 通过id得到用户地址
     * @param id
     * @return
     */
    UserAddress findUserAddressById(Long id);
}
