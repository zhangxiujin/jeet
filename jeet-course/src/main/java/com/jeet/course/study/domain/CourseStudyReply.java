package com.jeet.course.study.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CourseStudyReply {

    /**
     * 回答主键id
     */
    private Long id;

    /**
     * 回答内容
     */
    private String replyContent;

    /**
     * 语音保存路径
     */
    private String audioPath;

    /**
     * 回复人id
     */
    private Long replyId;

    /**
     * 问题id
     */
    private Long questionId;

    /**
     * 回答时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
