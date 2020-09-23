package com.jeffry.miaosha.service;

import com.jeffry.miaosha.error.BusinessException;
import com.jeffry.miaosha.service.model.ItemModel;

import java.util.List;

/**
 * @author ：JEFFRY
 * @version ：1.0.0
 * @classNamr ：ItemService
 * @date ：Created in 2020/9/14 15:33
 * @description：
 * @modified By：JEFFRY
 */
public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItem();

    //商品详情浏览
    ItemModel getItemById(Integer id);
    //减库存
    boolean decreaseStock(Integer itemId,Integer amount);

    //商品销量增加
    void increaseSales(Integer itemId,Integer amount)throws BusinessException;
}
