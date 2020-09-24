package com.jeffry.miaosha.service;

import com.jeffry.miaosha.error.BusinessException;
import com.jeffry.miaosha.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
}
