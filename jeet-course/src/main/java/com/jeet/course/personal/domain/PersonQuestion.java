package com.jeet.course.personal.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PersonQuestion {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 课程内容id
     */
    private Long contentId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 练习次数
     */
    private Integer number;

    /**
     * 题目类型
     * 0单词
     * 1选择
     * 2项目
     */
    private String bankType;

    /**
     * 题目分数
     */
    private Integer score;

    /**
     * 答案
     */
    private String answers;

    /**
     * 练习时间
     */
    private Date createTime;

    /**
     * 课程结构id
     */
    private Long structId;
}
