package com.jeet.course.check.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Desc: PracticeRecordVo
 * @author: xue
 * @Date: 2023/9/8 10:18
 */
@Data
public class PracticeRecordVo {

    /**
     * 练习id
     */
    private Long id;

    /**
     * 课程内容id
     */
    private Long contentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String nickName;

    /**
     * 班级名称
     */
    private String deptName;

    /**
     * 课程节名称
     */
    private String name;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 练习次数
     */
    private Integer number;

    /**
     * 练习成绩
     */
    private Integer score;

    /**
     * 审核状态
     */
    private String checkStatus;

    /**
     * 题目类型
     */
    private String bankType;

    /**
     * 练习时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
