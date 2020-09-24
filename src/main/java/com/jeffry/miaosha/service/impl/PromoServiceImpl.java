package com.jeffry.miaosha.service.impl;

import com.jeffry.miaosha.dao.PromoDOMapper;
import com.jeffry.miaosha.dataobject.PromoDO;
import com.jeffry.miaosha.service.PromoService;
import com.jeffry.miaosha.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author ：JEFFRY
 * @version ：1.0.0.0
 * @className ：PromoServiceImpl
 * @date ：Created in 2020/9/24 11:20
 * @description：秒杀服务实现类
 * @modified By：JEFFRY
 */
@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取商品秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        //do->model
        PromoModel promoModel = convertFromDataObject(promoDO);
        if (promoModel == null){
            return null;
        }

        //判断活动状态
        if (promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if (promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else {
            promoModel.setStatus(2);
        }
        return promoModel;
    }
    private PromoModel convertFromDataObject(PromoDO promoDO){
        if (promoDO == null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
