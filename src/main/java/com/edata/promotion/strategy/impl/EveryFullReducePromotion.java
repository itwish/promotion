package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.EveryFullReduce;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;
import static com.edata.promotion.util.Arith.*;
/**
 * 每(满)减
 * @author yugt 2023/6/20
 */
public class EveryFullReducePromotion implements Promotion<EveryFullReduce> {

    @Override
    public double getLowestPrice(Order<EveryFullReduce> order) {
        double price = order.getPrice();
        EveryFullReduce everyFullReduce = order.getPromotionData();
        double reduce = mul(Math.floor(div(price, everyFullReduce.getAmount())), everyFullReduce.getDiscount());
        Double maxReduce = everyFullReduce.getMaxReduce();
        double minPrice;
        if(maxReduce!=null){
            minPrice = sub(price, Math.min(reduce, maxReduce));
        }else{
            minPrice = sub(price, reduce);
        }
        return minPrice;
    }
}
