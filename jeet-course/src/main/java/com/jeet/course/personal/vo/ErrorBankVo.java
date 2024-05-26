package com.jeet.course.personal.vo;

import lombok.Data;

@Data
public class ErrorBankVo{
    /**
     * 题id
     */
    private Long bankId;

    /**
     * 正确 1/错误 0
     */
    private String correct;
}
