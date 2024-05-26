package com.jeet.course.check.domain;

import lombok.Data;

import java.sql.Date;

/**
 * @PackageName : com.jeet.course.check.domain
 * @FileName : Statistics
 * @Description : 学习进度实体类
 * @Author 李宇乐
 * @Date 2023/9/21 9:51
 * @Version : 1.0.0
 */
@Data
public class Statistics {
    private Long id;

    private Long structId;

    private Long contentId;

    private Long userId;

    private String isPlayComplete;

    private String studyRate;

    private Date createTime;
}
