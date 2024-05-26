package com.jeet.course.personal.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChooseErrorBankVo extends ErrorBankVo {
    /**
     * 选择标题
     */
    private String title;
    /**
     * 选项
     */
    private String options;
    /**
     * 类型 单选 0/多选 1
     */
    private String type;
    /**
     * 正确答案
     */
    private String correctAnswers;
    /**
     * 回答
     */
    private List<String> reply;

}
