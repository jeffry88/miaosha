package com.jeffry.miaosha.controller;

import com.jeffry.miaosha.error.BusinessException;
import com.jeffry.miaosha.error.EmBusinessError;
import com.jeffry.miaosha.response.CommonReturnType;
import com.jeffry.miaosha.service.OrderService;
import com.jeffry.miaosha.service.model.OrderModel;
import com.jeffry.miaosha.service.model.UserModel;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：JEFFRY
 * @version ：1.0.0.0
 * @classNamr ：OrderController
 * @date ：Created in 2020/9/23 13:31
 * @description：下单控制器
 * @modified By：JEFFRY
 */

@Slf4j
@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders="*")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    //封装下单请求
    @RequestMapping(value = "/createOrder",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "amount")Integer amount,
                                        @RequestParam(name = "promoId",required = false)Integer promoId) throws BusinessException {
        //获取用户登录信息
        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue() ){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        //获取登录用户信息
        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);
        return CommonReturnType.create(null);
    }
}
