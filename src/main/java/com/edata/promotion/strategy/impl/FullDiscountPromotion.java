package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

/**
 * 满折
 * @author yugt 2023/6/19
 */
public class FullDiscountPromotion implements Promotion {

    @Override
    public double getLowestPrice(Order order) {
        double price = order.getPrice();
        FullDiscount fullDiscount = (FullDiscount) order.getPromotionData();
        double minPrice;
        if(price>=fullDiscount.getAmount()){
            minPrice = (double) Math.round(price * fullDiscount.getDiscount()/10 * 100) / 100;
        } else {
            minPrice = price;
        }
        return minPrice;
    }
}
