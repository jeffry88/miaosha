package com.jeffry.miaosha.service.model;

import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @author ：JEFFRY
 * @version ：1.0.0.0
 * @classNamr ：promoModel
 * @date ：Created in 2020/9/23 14:31
 * @description：商品秒杀模型
 * @modified By：JEFFRY
 */
public class PromoModel {
    //秒杀活动id
    private Integer id;

    //秒杀状态1:未开始，2:进行中，3:已结束
    private Integer status;
    //秒杀活动名称
    private String promoName;

    //秒杀活动开始时间
    private DateTime startDate;

    //秒杀活动结束时间
    private DateTime endDate;
    //秒杀活动适用商品
    private Integer itemId;
    //秒杀活动商品价格
    private BigDecimal promoItemPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getPromoItemPrice() {
        return promoItemPrice;
    }

    public void setPromoItemPrice(BigDecimal promoItemPrice) {
        this.promoItemPrice = promoItemPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
