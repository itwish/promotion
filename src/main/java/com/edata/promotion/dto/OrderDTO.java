package com.edata.promotion.dto;

import com.edata.promotion.strategy.PromotionStrategy;
import lombok.Data;

/**
 * @author yugt 2023/6/19
 */
@Data
public class OrderDTO {
    /** 页面价格 */
    Double price;
    /** 促销信息 */
    String promotion;
    /** 促销类型：满减，每满减，折，混合 */
    PromotionStrategy tag;
}
