package com.edata.promotion.model;

import lombok.Data;

/**
 * 满减/满省/满折
 * @author yugt 2023/6/20
 */
@Data
public class FullDiscount {
    /** 满额/量 */
    double amount;
    /** 折扣 */
    double discount;

    public FullDiscount(double amount, double discount){
        this.amount = amount;
        this.discount = discount;
    }

    public FullDiscount(String amount, String discount){
        this.amount = Double.parseDouble(amount);
        this.discount = Double.parseDouble(discount);
    }
}
