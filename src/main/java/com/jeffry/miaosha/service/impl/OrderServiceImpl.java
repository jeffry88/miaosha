package com.jeffry.miaosha.service.impl;

import com.jeffry.miaosha.dao.OrderDOMapper;
import com.jeffry.miaosha.dataobject.OrderDO;
import com.jeffry.miaosha.error.BusinessException;
import com.jeffry.miaosha.error.EmBusinessError;
import com.jeffry.miaosha.service.ItemService;
import com.jeffry.miaosha.service.OrderService;
import com.jeffry.miaosha.service.UserService;
import com.jeffry.miaosha.service.model.ItemModel;
import com.jeffry.miaosha.service.model.OrderModel;
import com.jeffry.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author ：JEFFRY
 * @version ：1.0.0.0
 * @classNamr ：OrderServiceImpl
 * @date ：Created in 2020/9/21 15:34
 * @description：下单实现类
 * @modified By：JEFFRY
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //校验下单状态
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
        }
        if (amount <= 0 || amount >99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品超出购买限制");
        }

        //落单减库存
        boolean res = itemService.decreaseStock(itemId,amount);
        if (!res){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setTotalPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
        //生成订单时间
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();// 获取当前时间
        orderModel.setOrderTime(date);
        //生成订单号


        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //返回结果
        return null;
    }
    private String generateOrderNo(){
        //订单号16位

        //前8位位时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        now.format(DateTimeFormatter.ISO_DATE_TIME);
        //中间6位位自增序列
        //最后两位为分库分表位
        return null;
    }
    public static void main(String[] args){
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyy-MM-dd HH:ss:mm"));
        System.out.println(date);
    }
    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if (orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        return orderDO;
    }
}
