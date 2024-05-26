package com.jeet.course.study.vo;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

@Data
public class CourseStructureVo extends BaseEntity {
    /**
     * 结构id
     */
    private Long structId;
    /**
     * 结构名称
     */
    private String structName;
    /**
     * 结构类别
     */
    private String type;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 是否删除的标识
     */
    private String delFlag;

    /**
     * 学习进度
     */
    private Integer rate;

    /**
     * 内容id
     */
    private Long contentId;

    /**
     * 是否播放完成
     */
    private String isPlayComplete;

    /**
     * 学完数
     */
    private Integer completedCount;

    /**
     * 总数
     */
    private Integer sumCount;

}
