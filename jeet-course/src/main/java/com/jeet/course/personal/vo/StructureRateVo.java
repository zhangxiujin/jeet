package com.jeet.course.personal.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Desc: StructureRateVo
 * @author: xue
 * @Date: 2023/9/20 14:34
 */
@Data
public class StructureRateVo {

    /**
     * 课程id
     */
    private Long contentId;

    /**
     * 节点id
     */
    private Long structId;

    /**
     * 学习进度id
     */
    private Long rateId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 课程名称
     */
    private String structName;

    /**
     * 课程类型
     */
    private String type;

    /**
     * 父课程id
     */
    private Long parentId;

    /**
     * 学习时间
     */
    private Date createTime;

    /**
     * 是否播放完成
     */
    private String isPlayComplete = "0";

    /**
     * 学习进度
     */
    private Integer studyRate = 0;

}
