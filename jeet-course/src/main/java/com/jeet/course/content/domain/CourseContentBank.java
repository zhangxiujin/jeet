package com.jeet.course.content.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * 课程内容题库关联实体
 */
@Data
public class CourseContentBank extends BaseEntity {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 课程内容id
     */
    private Long contentId;
    /**
     * 课程题库id
     */
    private Long bankId;
    /**
     * 题库类型
     */
    private String type;
}
