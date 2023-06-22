package com.edata.promotion.service;

import com.edata.promotion.dto.OrderDTO;

/**
 * @author yugt 2023/6/20
 */
public interface IPriceService {
    Double getLowestPrice(OrderDTO orderDTO);
}
