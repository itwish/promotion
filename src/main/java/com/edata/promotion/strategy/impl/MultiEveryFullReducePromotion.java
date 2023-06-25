package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

import java.util.Comparator;
import java.util.List;

import static com.edata.promotion.util.Arith.*;

/**
 * 多个每满减
 * @author yugt 2023/6/21
 */
public class MultiEveryFullReducePromotion implements Promotion<List<FullDiscount>> {

    @Override
    public double getLowestPrice(Order<List<FullDiscount>> order) {
        double price = order.getPrice();
        List<FullDiscount> reduceList = order.getPromotionData();
        return reduceList.stream()
                .map(x->sub(price, mul(Math.floor(div(price,x.getAmount())), x.getDiscount())))
                .min(Comparator.naturalOrder())
                .get();
    }
}
