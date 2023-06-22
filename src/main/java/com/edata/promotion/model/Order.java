package com.edata.promotion.model;

import com.edata.promotion.strategy.PromotionStrategy;
import lombok.Data;

/**
 * 处理原始促销文本消息后生成的订单参数模型
 * @author yugt 2023/6/20
 */
@Data
public class Order {
    /**
     * 处理后的订单数据
     */
    Double price;

    /**
     * 提取后的结构化促销数据
     */
    Object promotionData;

    /**
     * 促销策略
     */
    PromotionStrategy promotionStrategy;

    public Order(Double price, Object promotionData, PromotionStrategy promotionStrategy) {
        this.price = price;
        this.promotionData = promotionData;
        this.promotionStrategy = promotionStrategy;
    }
}
