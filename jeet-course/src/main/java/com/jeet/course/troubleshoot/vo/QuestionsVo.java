package com.jeet.course.troubleshoot.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @PackageName : com.jeet.course.troubleshoot.vo
 * @FileName : QuestionsVo
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/25 14:12
 * @Version : 1.0.0
 */
@Data
public class QuestionsVo {

    /**
     *  主键id
     */
    private Long id;

    /**
     * contentId
     */
    private Long contentId;

    /**
     *  状态
     */
    private String status;

    /**
     *  提问内容
     */
    private String questionContent;

    /**
     * 提问语音
     */
    private String audioPath;

    /**
     *  用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 用户名称
     */
    private String nickName;

    /**
     * 用户所属
     */
    private String deptName;

    /**
     * 结构名称
     */
    private String name;

    /**
     * 课程结构id
     */
    private Long structId;
}
