package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.EveryFullReduce;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

/**
 * 每(满)减
 * @author yugt 2023/6/20
 */
public class EveryFullReducePromotion implements Promotion {

    @Override
    public double getLowestPrice(Order order) {
        double price = order.getPrice();
        EveryFullReduce everyFullReduce = (EveryFullReduce) order.getPromotionData();
        double reduce = Math.floor(price / everyFullReduce.getAmount()) * everyFullReduce.getDiscount();
        Double maxReduce = everyFullReduce.getMaxReduce();
        double minPrice;
        if(maxReduce!=null){
            minPrice = price - (reduce > maxReduce ? maxReduce : reduce);
        }else{
            minPrice = price - reduce;
        }
        return minPrice;
    }
}
