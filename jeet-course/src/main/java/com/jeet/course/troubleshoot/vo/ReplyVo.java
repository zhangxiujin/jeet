package com.jeet.course.troubleshoot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeet.course.study.domain.CourseStudyReply;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.jeet.course.troubleshoot.vo
 * @FileName : ReplyVo
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/26 11:27
 * @Version : 1.0.0
 */
@Data
public class ReplyVo {

    /**
     * 问题主键id
     */
    private Long questionId;

    /**
     *  提问内容
     */
    private String questionContent;

    /**
     * 提问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date quesTime;

    /**
     * 提问回复集合
     */
    private List<CourseStudyReply> courseStudyReplyList;

}
