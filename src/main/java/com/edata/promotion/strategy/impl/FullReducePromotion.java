package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

import static com.edata.promotion.util.Arith.*;

/**
 * 满减
 *
 * @author yugt 2023/6/20
 */
public class FullReducePromotion implements Promotion<FullDiscount> {

    @Override
    public double getLowestPrice(Order<FullDiscount> order) {
        double price = order.getPrice();
        FullDiscount fullDiscount = order.getPromotionData();
        double reduce = fullDiscount.getDiscount();
        double minPrice;
        if (gte(price, fullDiscount.getAmount())) {
            minPrice = sub(price, reduce);
        } else {
            minPrice = div((sub(mul(price, 2), reduce)), 2);
        }
        return minPrice;
    }
}
