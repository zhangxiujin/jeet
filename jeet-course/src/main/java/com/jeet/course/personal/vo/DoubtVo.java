package com.jeet.course.personal.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeet.course.study.domain.CourseStudyReply;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Desc: DoubtVo
 * @author: xue
 * @Date: 2023/9/27 16:19
 */
@Data
public class DoubtVo {

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
     * 所属课程
     */
    private String contentName;
    /**
     * 回答人姓名
     */
    private String replyName;
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
