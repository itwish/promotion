package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

import java.util.Comparator;
import java.util.List;

/**
 * 多个每满减
 * @author yugt 2023/6/21
 */
public class MultiEveryFullReducePromotion implements Promotion {

    @Override
    public double getLowestPrice(Order order) {
        double price = order.getPrice();
        List<FullDiscount> reduceList = (List<FullDiscount>) order.getPromotionData();
        return reduceList.stream()
                .map(x->price - Math.floor(price / x.getAmount()) * x.getDiscount())
                .min(Comparator.naturalOrder())
                .get();
    }
}
