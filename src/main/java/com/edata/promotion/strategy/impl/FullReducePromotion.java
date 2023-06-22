package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

/**
 * 满减
 * @author yugt 2023/6/20
 */
public class FullReducePromotion implements Promotion {

    @Override
    public double getLowestPrice(Order order) {
        double price = order.getPrice();
        FullDiscount fullDiscount = (FullDiscount) order.getPromotionData();
        double reduce = fullDiscount.getDiscount();
        double minPrice;
        if(price>= fullDiscount.getAmount()){
            minPrice = price - reduce;
        }else{
            minPrice = (price*2-reduce)/2;
        }
        return minPrice;
    }
}