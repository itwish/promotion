package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

import java.util.List;

import static com.edata.promotion.util.Arith.*;

/**
 * 多个满减
 *
 * @author yugt 2023/6/19
 */
public class MultiFullReducePromotion implements Promotion<List<FullDiscount>> {

    @Override
    public double getLowestPrice(Order<List<FullDiscount>> order) {
        List<FullDiscount> fullDiscountList = order.getPromotionData();
        Double price = order.getPrice();
        return fullDiscountList.stream()
                .mapToDouble(full -> gte(price, full.getAmount()) ? sub(price, full.getDiscount()) : price)
                .min()
                .getAsDouble();
    }
}
