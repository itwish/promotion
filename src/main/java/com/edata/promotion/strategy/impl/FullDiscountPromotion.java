package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

import static com.edata.promotion.util.Arith.*;

/**
 * 满折
 *
 * @author yugt 2023/6/19
 */
public class FullDiscountPromotion implements Promotion {

    @Override
    public double getLowestPrice(Order order) {
        double price = order.getPrice();
        FullDiscount fullDiscount = (FullDiscount) order.getPromotionData();
        double minPrice;
        if (gte(price, fullDiscount.getAmount())) {
            minPrice = mul(price, div(fullDiscount.getDiscount(), 10));
        } else {
            minPrice = price;
        }
        return minPrice;
    }
}
