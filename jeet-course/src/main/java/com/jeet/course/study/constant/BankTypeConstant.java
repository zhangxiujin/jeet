package com.jeet.course.study.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 题类型常量
 * @author zhangxiujin
 */
public class BankTypeConstant {
    public static final String WORD = "0";
    public static final String CHOOSE = "1";
    public static final String PROJECT = "2";
    public static final String SIMPLE = "3";
    public static final Map<String, String> TYPE_MAP;
    static {
        TYPE_MAP = new HashMap<>();
        TYPE_MAP.put("0", "单词");
        TYPE_MAP.put("1", "选择");
        TYPE_MAP.put("2", "项目");
        TYPE_MAP.put("3", "简答");
    }
}
