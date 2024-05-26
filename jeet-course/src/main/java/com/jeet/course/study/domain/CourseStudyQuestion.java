package com.jeet.course.study.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CourseStudyQuestion {
    /**
     * 提问id
     */
    private Long id;

    /**
     * 课程内容id
     */
    private Long contentId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 是否回答 0是未回答 1是已回答
     */
    private String status;

    /**
     * 疑问内容
     */
    private String questionContent;

    /**
     * 语音保存路径
     */
    private String audioPath;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 提问时间
     */
    private Date createTime;
}
