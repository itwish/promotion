package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

/**
 * 满省同满减 FullReducePromotion
 * @author yugt 2023/6/19
 */
public class FullSavePromotion implements Promotion {
    private FullReducePromotion fullDis = new FullReducePromotion();

    @Override
    public double getLowestPrice(Order order) {
        return fullDis.getLowestPrice(order);
    }
}
