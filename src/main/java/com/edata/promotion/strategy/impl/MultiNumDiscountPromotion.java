package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

import java.util.Comparator;
import java.util.List;

/**
 * 多个-购买数量折扣
 * @author yugt 2023/6/21
 */
public class MultiNumDiscountPromotion implements Promotion {

    @Override
    public double getLowestPrice(Order order) {
        double price = order.getPrice();
        List<FullDiscount> numDiscountList = (List<FullDiscount>) order.getPromotionData();
        return numDiscountList.stream()
                .map(x -> price * x.getDiscount()/10)
                .min(Comparator.naturalOrder())
                .map(d->(double) Math.round(d * 100) / 100)
                .get();
    }
}
