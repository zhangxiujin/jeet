package com.jeet.course.study.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;


/**
 * @PackageName : com.jeet.course.study.domain
 * @FileName : CoursePracticeRecord
 * @Description : 课程学习实体类
 * @Author 李宇乐
 * @Date 2023/8/30 16:02
 * @Version : 1.0.0
 */
@Data
public class CoursePracticeRecord {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 课程学习id
     */
    private Long contentId;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 课程结构
     */
    private String name;

    /**
     * 用户id
     */
    private Long userId;

    /**
     *  答题次数
     */
    private Integer number;

    /**
     * 题目类型 0是单词 1是选择 2是项目
     */
    private String bankType;

    /**
     * 学生分数
     */
    private Integer score;

    /**
     * 保存题目id、回答、答案
     */
    private String answers;

    /**
     * 练习时间
     */
    private Date createTime;

    /**
     * 审核状态
     */
    private String checkStatus;

    /**
     * 评语
     */
    private String comment;


}
