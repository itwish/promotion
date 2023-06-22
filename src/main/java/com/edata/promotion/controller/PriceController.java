package com.edata.promotion.controller;

import com.edata.promotion.dto.OrderDTO;
import com.edata.promotion.service.IPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 计算最低价格
 *
 * @author yugt 2021/10/12
 */
@Slf4j
@RestController
@RequestMapping("price")
public class PriceController {
    @Autowired
    private IPriceService priceService;

    /**
     * 计算最低价格
     *
     * @param orderDTO 页面价格，促销信息
     * @return 最低到手价
     */
    @RequestMapping("min")
    public double min(@RequestBody OrderDTO orderDTO){
        return priceService.getLowestPrice(orderDTO);
    }

}
