package com.edata.promotion.model;

import lombok.Data;

/**
 * 每(满)减
 * @author yugt 2023/6/20
 */
@Data
public class EveryFullReduce extends FullDiscount {
    /** 最多可减 */
    Double maxReduce;

    public EveryFullReduce(double amount, double reduce) {
        super(amount,reduce);
    }

    public EveryFullReduce(double amount, double reduce, Double maxReduce) {
        super(amount,reduce);
        this.maxReduce = maxReduce;
    }

    public EveryFullReduce(String amount, String reduce){
        super(amount,reduce);
    }
}
