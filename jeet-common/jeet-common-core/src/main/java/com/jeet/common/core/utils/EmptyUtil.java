package com.jeet.common.core.utils;

/**
 * @author zhangxiujin
 * @date 2021/6/28 12:49
 * @description 非空判断工具类
 */
public class EmptyUtil {

    public static final String UNDER_LINE = "_";

    public static final String STR_NULL = null;

    public static final Exception EXCEPTION_NULL = null;

    public static final String NULLSTR = "";

    public static final String SPACE_STR = " ";

    public static final String SEPARATOR = "/";

    public static final boolean isEmpty(Object obj) {
        if(obj == null) {
            return true;
        }
        return false;
    }

    public static final boolean isEmpty(String str) {
        if(str == null || str.equals("")) {
            return true;
        }
        return false;
    }

    public static final boolean isEmpty(Integer integer) {
        if(integer == null) {
            return true;
        }
        return false;
    }

    public static final boolean isNull(String str) {
        if(str == null) {
            return true;
        }
        return false;
    }
}
