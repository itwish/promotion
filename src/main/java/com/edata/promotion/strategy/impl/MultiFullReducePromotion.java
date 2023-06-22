package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

import java.util.List;

/**
 * 多个满减
 *
 * @author yugt 2023/6/19
 */
public class MultiFullReducePromotion implements Promotion {

    @Override
    public double getLowestPrice(Order order) {
        List<FullDiscount> fullDiscountList = (List<FullDiscount>) order.getPromotionData();
        Double price = order.getPrice();
        return fullDiscountList.stream()
                .mapToDouble(full -> price >= full.getAmount() ? price - full.getDiscount() : price)
                .min()
                .getAsDouble();
    }
}
