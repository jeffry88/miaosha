package com.jeffry.miaosha.service.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：JEFFRY
 * @version ：
 * @classNamr ：OrderModel
 * @date ：Created in 2020/9/21 14:57
 * @description：用户下单交易模型
 * @modified By：JEFFRY
 */
public class OrderModel {
    //订单号
    private String id;
    //下单用户
    private Integer userId;
    //商品id
    private Integer itemId;
    //购买商品单价
    private BigDecimal itemPrice;
    //购买数量
    private Integer amount;
    //购买金额
    private BigDecimal totalPrice;
    //下单时间
    private Date orderTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
