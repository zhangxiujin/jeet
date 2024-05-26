package com.jeet.course.content.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 课程内容实体
 */
@Data
public class CourseContent extends BaseEntity {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 结构id
     */
    private Long structId;
    /**
     * 课程内容名称
     */
    private String name;
    /**
     * 描述
     */
    private String desc;
}
