package com.jeffry.miaosha.service;

import com.jeffry.miaosha.service.model.PromoModel;
import org.springframework.stereotype.Repository;

public interface PromoService {
    PromoModel getPromoByItemId(Integer itemId);
}
