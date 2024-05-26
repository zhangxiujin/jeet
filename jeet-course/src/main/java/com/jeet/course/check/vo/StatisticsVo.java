package com.jeet.course.check.vo;

import lombok.Data;

/**
 * @PackageName : com.jeet.course.check.vo
 * @FileName : StatisticsVo
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/20 15:57
 * @Version : 1.0.0
 */
@Data
public class StatisticsVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 结构id
     */
    private Long structId;

    /**
     * 课程id
     */
    private Long contentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否完成
     */
    private String isPlayComplete;

    /**
     * 完成率
     */
    private String studyRate;

    /**
     *
     */
    private String createTime;
}
