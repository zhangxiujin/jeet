package com.jeet.common.core.utils;

import java.math.BigDecimal;

/**
 * @author: zhangxiujin
 * @date: 2022/5/1 16:18
 * @descripton
 */
public class AmountUtil {

    public static Double cent2Yuan(String amount) {
        BigDecimal bigDecimal = new BigDecimal(amount);
        BigDecimal yuan = bigDecimal.divide(new BigDecimal("100"));
        return yuan.doubleValue();
    }

    public static Integer yuan2Cent(String amount) {
        BigDecimal bigDecimal = new BigDecimal(amount);
        BigDecimal cent = bigDecimal.multiply(new BigDecimal("100"));
        return cent.intValue();
    }
}
