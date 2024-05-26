package com.jeet.course.study.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeet.course.study.domain.CourseStudyReply;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionReplyVo{
    /**
     * 提问主键id
     */
    private Long questionId;
    /**
     * 课程内容id
     */
    private Long contentId;
    /**
     * 未回答或已回答 0未回答 1已回答
     */
    private String status;
    /**
     * 提问内容
     */
    private String questionContent;
    /**
     * 提问用户id
     */
    private Long userId;
    /**
     * 提问用户姓名
     */
    private String nickName;
    /**
     * 所属部门
     */
    private String deptName;
    /**
     * 回答人姓名
     */
    private String replyName;
    /**
     * 提问语音
     */
    private String audioPath;
    /**
     * 提问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date questionCreateTime;

    /**
     * 回复
     */
    private List<CourseStudyReply> courseStudyReplyList;

}
