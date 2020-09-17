package com.jeffry.miaosha.service;

import com.jeffry.miaosha.error.BusinessException;
import com.jeffry.miaosha.service.model.UserModel;

public interface UserService {
    /**
     * @Author: JEFFRY
     * @Description: 通过用户id获取用户对象的方法
     * @Date: 2020/9/11 13:53
     * @param id
     * @return com.jeffry.miaosha.service.model.UserModel
     */
    UserModel getUserById(Integer id);
    /**
     * @Author: JEFFRY
     * @Description: 用户注册
     * @Date: 2020/9/11 13:52
     * @param userModel
     * @return void
     */
    void register(UserModel userModel) throws BusinessException;

    /**
     * @Author: JEFFRY
     * @Description: 校验用户登录
     * @Date: 2020/9/11 14:00
     * @param telphone 用户注册手机号
     * @param encrptPassword 用户加密后密码
     * @return com.jeffry.miaosha.service.model.UserModel
     */
    UserModel validateLogin(String telphone,String encrptPassword) throws BusinessException;
}
