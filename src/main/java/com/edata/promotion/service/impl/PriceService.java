package com.edata.promotion.service.impl;

import com.edata.promotion.dto.OrderDTO;
import com.edata.promotion.etl.DataPipeline;
import com.edata.promotion.model.Order;
import com.edata.promotion.service.IPriceService;
import com.edata.promotion.strategy.PromotionContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * 获取最低价
 * @author yugt 2023/6/20
 */
@Slf4j
@Service
public class PriceService implements IPriceService {

    @Autowired
    private DataPipeline dataPipeline;

    /**
     * 获取最低价通用方法
     * @param orderDTO
     * @return
     */
    @Override
    public Double getLowestPrice(OrderDTO orderDTO) {
        List<Function<OrderDTO, Order>> handlerPipeline = dataPipeline.getHandlerPipeline();
        if(StringUtils.isBlank(orderDTO.getPromotion())){
            return orderDTO.getPrice();
        }
        double minPrice = Double.MAX_VALUE;
        for (Function<OrderDTO, Order> handlerFn : handlerPipeline) {
            Order order = handlerFn.apply(orderDTO);
            if (order == null) {
                continue;
            }
            if(order.getPromotionData()==null){
                minPrice = orderDTO.getPrice();
                break;
            }
            // 由于组合促销是由基本促销策略组成，因此需要比较过程值与最小值
            double tmpMin = PromotionContext.doPromotion(order);
            minPrice = Math.min(minPrice, tmpMin);
        }

        return minPrice;
    }
}
