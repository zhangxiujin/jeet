package com.jeet.common.core.utils;

/**
 * @author zhangxiujin
 * @date 2017-07-10
 * @description 获取随机数工具类
 */
public class Random {
    /**
     * 功能描述：获取六位随机数
     * 参数描述： 无
     *		 @time 2017-07-10
     */
    public static String code() {
        int n = 0 ;
        while(n < 100000){
            n = (int)(Math.random()*1000000);
        }
        return String.valueOf(n);
    }

    /**
     * 功能描述：获取8位随机数
     * 参数描述： 无
     *       @time 2017-07-10
     */
    public static String eightcode() {
        int n = 0 ;
        while(n < 10000000){
            n = (int)(Math.random()*100000000);
        }
        return String.valueOf(n);
    }

    /**
     * 功能描述：获取5位随机数
     * 参数描述： 无
     *       @time 2017-07-10
     */
    public static String fivecode() {
        long n = 0 ;
        while(n < 10000){
            n = (long)(Math.random()*1000*100);
        }
        return String.valueOf(n);
    }

}
