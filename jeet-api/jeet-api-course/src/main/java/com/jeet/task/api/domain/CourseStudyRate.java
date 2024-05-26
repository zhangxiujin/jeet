package com.jeet.task.api.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Desc: PersonalStudyRate
 * @author: xue
 * @Date: 2023/9/14 16:55
 */
@Data
public class CourseStudyRate {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 节点id
     */
    private Long structId;

    /**
     * 内容id
     */
    private Long contentId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否播放完成, 默认是未完成
     */
    private String isPlayComplete = "0";

    /**
     * 学习进度
     */
    private Integer studyRate;

    /**
     * 创建时间
     */
    private Date createTime;

}
