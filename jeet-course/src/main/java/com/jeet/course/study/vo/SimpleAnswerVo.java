package com.jeet.course.study.vo;

import lombok.Data;

/**
 * @Desc: SimpleAnswerVo
 * @author: xue
 * @Date: 2023/9/4 17:24
 */
@Data
public class SimpleAnswerVo {

    /**
     * 简答题id
     */
    private Long bankId;

    /**
     * 简答回答
     */
    private String inputAnswer;

}
