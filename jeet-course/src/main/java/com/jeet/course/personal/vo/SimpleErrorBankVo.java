package com.jeet.course.personal.vo;

import lombok.Data;

@Data
public class SimpleErrorBankVo extends ErrorBankVo{

    /**
     * 简答题名称
     */
    private String simpleName;

    /**
     * 简单描述
     */
    private String details;

    /**
     * 输入的回答
     */
    private String inputAnswer;


}
