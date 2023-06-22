package com.edata;

import com.edata.promotion.PromotionApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

/**
 * @author yugt 2020/7/27
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PromotionApplication.class)
public class PriceControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * 注意：测试时要保证rawSales中的输入项与referMinPrices中数据的位置对应
     */
    @Test
    public void testMinPrice() {
        String rawSales;
        {
            rawSales = """
                页面价格: 1169
                促销信息: 购买至少1件时可享受优惠,满1249元减130元
                页面价格: 1099
                促销信息: 满1499减400,满899减300,满499减150,满499减100,满99减15
                页面价格: 2019
                促销信息: 满1099元,省500元
                页面价格: 629
                促销信息: 每300减50,可跨店,上不封顶
                页面价格:309
                促销信息: 每满300元,省50元
                页面价格: 4948
                促销信息: 每满299元，可减50元现金，最多可减1000元
                页面价格: 1599
                促销信息: 每满1399元，可减300元现金,每满1299元，可减100元现金
                页面价格: 349
                促销信息: 下单再享9.5折
                页面价格: 2100
                促销信息: 满1件,打4.28折
                页面价格: 5319
                促销信息: 满399享9.5折
                页面价格: 339
                促销信息: 满2件，总价打9.80折；满3件，总价打9.50折
                页面价格: 2100
                促销信息: 每300减50,可跨店,上不封顶,满1件,打4.28折,
                页面价格: 339
                促销信息: 满599元减30元，满999元减50元，满1888元减100元,满2件，总价打9.80折；满3件，总价打9.50折,
                页面价格: 955
                促销信息: 淘金币可抵18.78元起,
                页面价格: 2304
                促销信息:
            """;
        }

        List<String> lines = rawSales.lines().toList();
        double[] referMinPrices = new double[]{1104,799,1519,529,259,4148,1299,331.55,898.8,5053.05,322.05,898.8,322.05,955,2304};
        int j=0;
        record Param(double price,String promotion){}
        for (int i = 0; i < lines.size(); i++) {
            String priceText = lines.get(i);
            String promotionText = lines.get(++i);
            System.out.println(priceText.trim());
            System.out.println(promotionText.trim());
            double price = Double.parseDouble(priceText.split(":")[1].trim());
            String[] sales = promotionText.split(":");
            String promotion = sales.length>1?sales[1]:"";
            Param param = new Param(price,promotion);

            String actualMinPrice = testRestTemplate.postForObject("/price/min", param, String.class);
            System.out.println("最低到手价: "+actualMinPrice);
            System.out.println();
            Assertions.assertEquals(referMinPrices[j++], Double.parseDouble(actualMinPrice));
        }

    }

}
