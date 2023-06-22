package com.edata.promotion.strategy.impl;

import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.Promotion;

import static com.edata.promotion.util.Arith.*;

/**
 * 按购买数量计算折扣后的最低价
 * @author yugt 2023/6/20
 */
public class NumDiscountPromotion implements Promotion {

    /**
     * 注意：计算最低价的话不必乘以购买数量再除以购买数量，直接用价格*折扣即可
     * 339*3*0.95/3
     * 可化简为：
     * 339*0.95
     * @param order
     * @return
     */
    @Override
    public double getLowestPrice(Order order) {
        double price = order.getPrice();
        FullDiscount numDiscount = (FullDiscount) order.getPromotionData();
        return mul(price, div(numDiscount.getDiscount(),10));
    }
}
