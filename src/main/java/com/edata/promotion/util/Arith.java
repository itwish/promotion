package com.edata.promotion.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 加减乘除计算（无精度丢失问题）
 *
 * @author yugt 2023/6/22
 */
public class Arith {

    /**
     * 加法：四舍五入，保留两位小数
     *
     * @param x
     * @param y
     * @return
     */
    public static double add(double x, double y) {
        BigDecimal a = BigDecimal.valueOf(x);
        BigDecimal b = BigDecimal.valueOf(y);
        return a.add(b).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 减法：四舍五入，保留两位小数
     *
     * @param x
     * @param y
     * @return
     */
    public static double sub(double x, double y) {
        BigDecimal a = BigDecimal.valueOf(x);
        BigDecimal b = BigDecimal.valueOf(y);
        return a.subtract(b).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 乘法：四舍五入，保留两位小数
     *
     * @param x
     * @param y
     * @return
     */
    public static double mul(double x, double y) {
        BigDecimal a = BigDecimal.valueOf(x);
        BigDecimal b = BigDecimal.valueOf(y);
        return a.multiply(b).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 除法：四舍五入，保留3位有效数字
     *
     * @param x
     * @param y
     * @return
     */
    public static double div(double x, double y) {
        BigDecimal a = BigDecimal.valueOf(x);
        BigDecimal b = BigDecimal.valueOf(y);
        return a.divide(b, 3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 大于等于
     *
     * @param x
     * @param y
     * @return
     */
    public static boolean gte(double x, double y) {
        BigDecimal a = BigDecimal.valueOf(x);
        BigDecimal b = BigDecimal.valueOf(y);
        int ret = a.compareTo(b);
        return ret >= 0 ? true : false;
    }

    /**
     * 大于
     *
     * @param x
     * @param y
     * @return
     */
    public static boolean gt(double x, double y) {
        BigDecimal a = BigDecimal.valueOf(x);
        BigDecimal b = BigDecimal.valueOf(y);
        int ret = a.compareTo(b);
        return ret > 0 ? true : false;
    }

    /**
     * 等于
     *
     * @param x
     * @param y
     * @return
     */
    public static boolean equal(double x, double y) {
        BigDecimal a = BigDecimal.valueOf(x);
        BigDecimal b = BigDecimal.valueOf(y);
        int ret = a.compareTo(b);
        return ret == 0 ? true : false;
    }

}
