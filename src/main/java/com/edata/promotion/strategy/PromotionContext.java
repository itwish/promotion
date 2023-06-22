package com.edata.promotion.strategy;

import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.impl.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 销售策略容器
 * @author yugt 2023/6/19
 */
@Component
public class PromotionContext {
    private static Map<PromotionStrategy, Promotion> salesMap = new HashMap<>();

    static {
        salesMap.put(PromotionStrategy.FULL_REDUCE, new FullReducePromotion());
        salesMap.put(PromotionStrategy.MULTI_FULL_REDUCE, new MultiFullReducePromotion());
        salesMap.put(PromotionStrategy.FULL_SAVE, new FullSavePromotion());
        salesMap.put(PromotionStrategy.EVERY_REDUCE, new EveryFullReducePromotion());
        salesMap.put(PromotionStrategy.MULTI_EVERY_REDUCE, new MultiEveryFullReducePromotion());
        salesMap.put(PromotionStrategy.NUM_DISCOUNT, new NumDiscountPromotion());
        salesMap.put(PromotionStrategy.FULL_DISCOUNT, new FullDiscountPromotion());
        salesMap.put(PromotionStrategy.MULTI_NUM_DISCOUNT, new MultiNumDiscountPromotion());
    }

    public static double doPromotion(Order order){
        Promotion promotion = salesMap.get(order.getPromotionStrategy());
        return promotion.getLowestPrice(order);
    }

}
