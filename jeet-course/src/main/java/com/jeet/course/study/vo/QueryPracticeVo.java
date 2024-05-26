package com.jeet.course.study.vo;

import lombok.Data;

/**
 * @Desc: QueryPracticeVo
 * @author: xue
 * @Date: 2023/9/1 14:48
 */
@Data
public class QueryPracticeVo {

    /**
     * 内容id
     */
    private Long contentId;

    /**
     * 题目类型
     */
    private String type;

    /**
     * 用户id
     */
    private Long userId;

}
