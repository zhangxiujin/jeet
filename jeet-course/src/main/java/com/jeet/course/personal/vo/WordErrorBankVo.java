package com.jeet.course.personal.vo;

import lombok.Data;

@Data
public class WordErrorBankVo extends ErrorBankVo {
    /**
     * 英文
     */
    private String enName;
    /**
     * 中文回答
     */
    private String wordAnswer;
    /**
     * 正确答案
     */
    private String answer;
}
