package com.nju.service.impl;

import com.nju.dao.UserAccountDao;
import com.nju.dao.UserAddressDao;
import com.nju.dao.UserDao;
import com.nju.enums.ResultMessage;
import com.nju.enums.UserState;
import com.nju.model.User;
import com.nju.model.UserAccount;
import com.nju.model.UserAddress;
import com.nju.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserAddressDao userAddressDao;
    private UserDao userDao;
    private UserAccountDao userAccountDao;

    @Autowired
    private UserServiceImpl(UserAddressDao userAddressDao, UserDao userDao, UserAccountDao userAccountDao){
        this.userAddressDao = userAddressDao;
        this.userDao = userDao;
        this.userAccountDao = userAccountDao;
    }

    @Override
    public ResultMessage addUserAddress(UserAddress userAddress) {
        UserAddress userAddress1 = userAddressDao.save(userAddress);
        if(userAddress1!=null){
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }

    @Override
    public User sign(String mail) {
        List<User> users = userDao.findByMail(mail);
        User resUser;
        if(users.size()==0){
            User user = new User(mail);
            resUser = userDao.save(user);
            Long userId = resUser.getId();
            System.out.println("userId "+userId);
            UserAccount userAccount = new UserAccount(userId);
            UserAccount userAccount1 = userAccountDao.save(userAccount);
        }else{
            resUser = users.get(0);
        }
        return resUser;
    }

    @Override
    public ResultMessage updateAvatar(long userId, String avatar) {
        User user = userDao.findOne(userId);
        user.setAvatar(avatar);
        User user1 = userDao.save(user);
        if(user1==null){
            return ResultMessage.NOT_EXIST;
        }else{
            return ResultMessage.SUCCESS;
        }
    }

    @Override
    public User findUserById(Long id) {
        User user = userDao.findOne(id);
        if(user==null){
            return null;
        }else{
            return user;
        }
    }

    @Override
    public ResultMessage saveName(Long id, String name) {
        User user = userDao.findOne(id);
        user.setName(name);
        User user1 = userDao.save(user);
        return ResultMessage.SUCCESS;
    }

    @Override
    public List<UserAddress> getUserAddress(Long userId) {
        List<UserAddress> userAddresses = userAddressDao.findByUserId(userId);
        return userAddresses;
    }

    @Override
    public ResultMessage modifyState(Long id) {
        User user = userDao.findOne(id);
        if(user!=null){
            user.setUserState(UserState.NOTVALID);
            User user1 = userDao.save(user);
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.NOT_EXIST;
        }
    }

    @Override
    public Double getAccountById(Long id) {
        List<UserAccount> userAccounts = userAccountDao.findByUserId(id);
        return userAccounts.get(0).getAccount();
    }

    @Override
    public ResultMessage modifyUserAddress(UserAddress userAddress) {
        UserAddress userAddress1 = userAddressDao.save(userAddress);
        if(userAddress1!=null){
            return ResultMessage.SUCCESS;
        }else{
            return ResultMessage.FAILED;
        }
    }

    @Override
    public ResultMessage deleteAddress(Long id) {
        userAddressDao.delete(id);
        return ResultMessage.SUCCESS;
    }

    @Override
    public UserAddress findUserAddressById(Long id) {
        return userAddressDao.findOne(id);
    }
}
