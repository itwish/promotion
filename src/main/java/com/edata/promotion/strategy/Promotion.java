package com.edata.promotion.strategy;

import com.edata.promotion.model.Order;

/**
 * @author yugt 2023/6/19
 * 促销最低价核心接口
 */
public interface Promotion {
    double getLowestPrice(Order order);
}
