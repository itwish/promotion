package com.edata.promotion.strategy;

/**
 * 促销折扣策略枚举
 * @author yugt 2023/6/19
 */
public enum PromotionStrategy {
    /** 满减 */
    FULL_REDUCE,
    /** 多个满减 */
    MULTI_FULL_REDUCE,
    /** 满省 */
    FULL_SAVE,
    /** 每(满)减 */
    EVERY_REDUCE,
    /** 多个每(满)减 */
    MULTI_EVERY_REDUCE,
    /** 购买数量折扣 */
    NUM_DISCOUNT,
    /** 满价折扣 */
    FULL_DISCOUNT,
    /** 多个 购买数量折扣 */
    MULTI_NUM_DISCOUNT,
    /** 忽略 */
    IGNORE;
}
