package com.jeffry.miaosha.service.impl;

import com.jeffry.miaosha.dao.ItemDOMapper;
import com.jeffry.miaosha.dao.ItemStockDOMapper;
import com.jeffry.miaosha.dataobject.ItemDO;
import com.jeffry.miaosha.dataobject.ItemStockDO;
import com.jeffry.miaosha.error.BusinessException;
import com.jeffry.miaosha.error.EmBusinessError;
import com.jeffry.miaosha.service.ItemService;
import com.jeffry.miaosha.service.model.ItemModel;
import com.jeffry.miaosha.validator.ValidationResult;
import com.jeffry.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：JEFFRY
 * @version ：1.0.0
 * @classNamr ：ItemSrviceImpl
 * @date ：Created in 2020/9/14 17:21
 * @description：商品实现类
 * @modified By：JEFFRY
 */
@Service
public class ItemSrviceImpl implements ItemService {

    @Autowired
    ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    private ItemDO converItemDOFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }
    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }
    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        ValidationResult result = validator.validate(itemModel);
        //校验入参
        if (result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //转化itemmodel->dataobject
        ItemDO itemDO = this.converItemDOFromItemModel(itemModel);
        //写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        return null;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
         if (itemDO == null){
             return null;
         }
         ItemStockDO itemStockDO = itemStockDOMapper.selectByPrimaryKey(itemDO.getId());

         //将dataobject -> model
        ItemModel itemModel = converModelFromDataObject(itemDO,itemStockDO);
        return itemModel;
    }

    private ItemModel converModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(BigDecimal.valueOf(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}