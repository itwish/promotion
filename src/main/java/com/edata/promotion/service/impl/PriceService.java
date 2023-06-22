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
 * @author yugt 2023/6/20
 */
@Slf4j
@Service
public class PriceService implements IPriceService {

    @Autowired
    private DataPipeline dataPipeline;

    /**
     * @param orderDTO
     * @return
     */
    @Override
    public Double getLowestPrice(OrderDTO orderDTO) {
        List<Function<OrderDTO, Order>> handlerChain = dataPipeline.getHandlerChain();
        if(StringUtils.isBlank(orderDTO.getPromotion())){
            return orderDTO.getPrice();
        }
        double minPrice = Double.MAX_VALUE;
        for (Function<OrderDTO, Order> fn : handlerChain) {
            Order order = fn.apply(orderDTO);
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
