package com.jeffry.miaosha.service.impl;

import com.jeffry.miaosha.dao.OrderDOMapper;
import com.jeffry.miaosha.dao.SequenceDOMapper;
import com.jeffry.miaosha.dataobject.OrderDO;
import com.jeffry.miaosha.dataobject.SequenceDO;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //校验下单状态
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户不存在");
        }
        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品超出购买限制");
        }

        //落单减库存
        boolean res = itemService.decreaseStock(itemId, amount);
        if (!res) {
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
        Date date = new Date();
        Timestamp now = new Timestamp(date.getTime());
        orderModel.setOrderTime(now);
        //生成订单号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //销量更新
        itemService.increaseSales(itemId,amount);
        //返回结果
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected String generateOrderNo() {
        //订单号22位
        StringBuilder stringBuilder = new StringBuilder();
        //前14位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String nowDate = dtf2.format(now);
        stringBuilder.append(nowDate);
        //中间6位位自增序列
        //获取当前sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceStr.length(); i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        //最后两位为分库分表位,写死
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setTotalPrice(orderModel.getTotalPrice().doubleValue());
        return orderDO;
    }
}
