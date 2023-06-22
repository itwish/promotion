package com.edata.promotion.etl;

import com.edata.promotion.dto.OrderDTO;
import com.edata.promotion.model.EveryFullReduce;
import com.edata.promotion.model.FullDiscount;
import com.edata.promotion.model.Order;
import com.edata.promotion.strategy.PromotionStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本数据处理流水线：匹配，分类，提取，转换，分发
 * 1.用正则表达式匹配，提取数据
 * 2.将数据转换为抽象数据模型
 * 3.分发给不同的促销策略类计算最低价
 * 说明：该类也可以采用 策略模式 或 责任链模式 进行拆分，但 lambda 方式更简洁
 *
 * @author yugt 2023/6/20
 */
@Component
public class DataPipeline {
    /**
     * 满减 正则表达式
     */
    @Value("${promotion.pattern.fullReduce}")
    private String fullReduce;

    @Value("${promotion.pattern.multiFullReduce}")
    private String multiFullReduce;

    @Value("${promotion.pattern.fullSave}")
    private String fullSave;

    @Value("${promotion.pattern.everyReduce1}")
    private String everyReduce1;

    @Value("${promotion.pattern.everyReduce2}")
    private String everyReduce2;

    @Value("${promotion.pattern.everyReduce3}")
    private String everyReduce3;

    @Value("${promotion.pattern.everyReduce4}")
    private String everyReduce4;

    @Value("${promotion.pattern.discount1}")
    private String discount1;

    @Value("${promotion.pattern.discount2}")
    private String discount2;

    @Value("${promotion.pattern.discount3}")
    private String discount3;

    /** 多个按购买数量折扣 */
    @Value("${promotion.pattern.discount4}")
    private String discount4;

    /** 忽略模式 */
    @Value("${promotion.pattern.ignore}")
    private String ignore;

    private Pattern fullReducePattern;
    private Pattern multiFullReducePattern;
    private Pattern fullSavePattern;
    private Pattern everyReducePattern1;
    private Pattern everyReducePattern2;
    private Pattern everyReducePattern3;
    private Pattern everyReducePattern4;
    private Pattern discountPattern1;
    private Pattern discountPattern2;
    private Pattern discountPattern3;
    private Pattern discountPattern4;
    private Pattern ignorePattern;

    @PostConstruct
    public void init() {
        fullReducePattern = Pattern.compile(fullReduce);
        fullSavePattern = Pattern.compile(fullSave);
        everyReducePattern1 = Pattern.compile(everyReduce1);
        everyReducePattern2 = Pattern.compile(everyReduce2);
        everyReducePattern3 = Pattern.compile(everyReduce3);
        everyReducePattern4 = Pattern.compile(everyReduce4);
        multiFullReducePattern = Pattern.compile(multiFullReduce);
        discountPattern1 = Pattern.compile(discount1);
        discountPattern2 = Pattern.compile(discount2);
        discountPattern3 = Pattern.compile(discount3);
        discountPattern4 = Pattern.compile(discount4);
        ignorePattern = Pattern.compile(ignore);
    }

    /**
     * 用正则表达式匹配，提取原始文本数据中的有效数据
     * 转换成抽象后的数据模型，例如满减参数类：FullDiscount
     */
    private BiFunction<OrderDTO, Pattern, Order> fullFn = (orderDTO, pattern) -> {
        Matcher matcher = pattern.matcher(orderDTO.getPromotion());
        FullDiscount fullDiscount = null;
        if (matcher.find()) {
            int a = matcher.groupCount();
            if(a==1){
                fullDiscount = new FullDiscount("1", matcher.group(a));
            }else{
                fullDiscount = new FullDiscount(matcher.group(a-1), matcher.group(a));
            }
        }
        Order order = null;
        if (fullDiscount != null) {
            order = new Order(orderDTO.getPrice(), fullDiscount, orderDTO.getTag());
        }
        return order;
    };

    private BiFunction<OrderDTO, Pattern, Order> everyFn = (orderDTO, pattern) -> {
        Matcher matcher = pattern.matcher(orderDTO.getPromotion());
        EveryFullReduce everyFullReduce = null;
        if (matcher.find()) {
            int a = 1;
            everyFullReduce = new EveryFullReduce(matcher.group(a), matcher.group(a + 1));
            if (matcher.groupCount() > 2) {
                everyFullReduce.setMaxReduce(Double.parseDouble(matcher.group(a + 2)));
            }
        }
        Order order = null;
        if (everyFullReduce != null) {
            order = new Order(orderDTO.getPrice(), everyFullReduce, PromotionStrategy.EVERY_REDUCE);
        }
        return order;
    };

    private BiFunction<OrderDTO, Pattern, Order> multiReduceFn = (orderDTO, pattern) -> {
        Matcher matcher = pattern.matcher(orderDTO.getPromotion());
        List<FullDiscount> fullDiscountList = new ArrayList<>();
        int matcher_start = 0;
        while (matcher.find(matcher_start)) {
            FullDiscount fullDiscount = new FullDiscount(matcher.group(1), matcher.group(2));
            fullDiscountList.add(fullDiscount);
            matcher_start = matcher.end();
        }
        Order order = null;
        if (fullDiscountList.size() > 0 && orderDTO.getTag() != null) {
            order = new Order(orderDTO.getPrice(), fullDiscountList, orderDTO.getTag());
        }
        return order;
    };

    private Function<OrderDTO, Order> fullReduceFn = orderDTO -> {
        orderDTO.setTag(PromotionStrategy.FULL_REDUCE);
        return fullFn.apply(orderDTO, fullReducePattern);
    };

    /** 多个满减 */
    private Function<OrderDTO, Order> multiFullReduceFn = orderDTO -> {
        orderDTO.setTag(PromotionStrategy.MULTI_FULL_REDUCE);
        return multiReduceFn.apply(orderDTO, multiFullReducePattern);
    };

    private Function<OrderDTO, Order> fullSaveFn = orderDTO -> {
        orderDTO.setTag(PromotionStrategy.FULL_SAVE);
        return fullFn.apply(orderDTO, fullSavePattern);
    };

    private Function<OrderDTO, Order> everyReduceFn1 = orderDTO -> everyFn.apply(orderDTO, everyReducePattern1);

    private Function<OrderDTO, Order> everyReduceFn2 = orderDTO -> everyFn.apply(orderDTO, everyReducePattern2);

    private Function<OrderDTO, Order> everyReduceFn3 = orderDTO -> everyFn.apply(orderDTO, everyReducePattern3);

    /** 多个每减 */
    private Function<OrderDTO, Order> everyReduceFn4 = orderDTO -> {
        orderDTO.setTag(PromotionStrategy.MULTI_EVERY_REDUCE);
        return multiReduceFn.apply(orderDTO, everyReducePattern4);
    };

    /** 按数量折扣 */
    private Function<OrderDTO, Order> discountFn1 = orderDTO -> {
        orderDTO.setTag(PromotionStrategy.NUM_DISCOUNT);
        return fullFn.apply(orderDTO, discountPattern1);
    };

    private Function<OrderDTO, Order> discountFn2 = orderDTO -> {
        orderDTO.setTag(PromotionStrategy.NUM_DISCOUNT);
        return fullFn.apply(orderDTO, discountPattern2);
    };
    /** 按金额折扣 */
    private Function<OrderDTO, Order> discountFn3 = orderDTO -> {
        orderDTO.setTag(PromotionStrategy.FULL_DISCOUNT);
        return fullFn.apply(orderDTO, discountPattern3);
    };

    /** 多个满折 */
    private Function<OrderDTO, Order> discountFn4 = orderDTO -> {
        orderDTO.setTag(PromotionStrategy.MULTI_NUM_DISCOUNT);
        return multiReduceFn.apply(orderDTO, discountPattern4);
    };

    /** 忽略处理 */
    private Function<OrderDTO, Order> ignoreFn = orderDTO -> {
        Matcher matcher = ignorePattern.matcher(orderDTO.getPromotion());
        Order order = null;
        if (matcher.find()) {
            order = new Order(orderDTO.getPrice(), null, PromotionStrategy.IGNORE);
        }
        return order;
    };

    private List<Function<OrderDTO, Order>> handlerChain = new ArrayList<>();

    public DataPipeline() {
        handlerChain.add(ignoreFn);
        handlerChain.add(fullReduceFn);
        handlerChain.add(multiFullReduceFn);
        handlerChain.add(fullSaveFn);
        handlerChain.add(everyReduceFn1);
        handlerChain.add(everyReduceFn2);
        handlerChain.add(everyReduceFn3);
        handlerChain.add(everyReduceFn4);
        handlerChain.add(discountFn1);
        handlerChain.add(discountFn2);
        handlerChain.add(discountFn3);
        handlerChain.add(discountFn4);
    }

    public List<Function<OrderDTO, Order>> getHandlerChain() {
        return this.handlerChain;
    }

}
