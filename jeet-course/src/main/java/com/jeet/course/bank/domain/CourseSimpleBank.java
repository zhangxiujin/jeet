package com.jeet.course.bank.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Desc: CourseSimpleBank
 * @author: xue
 * @Date: 2023/9/2 23:05
 */
@Data
public class CourseSimpleBank extends BaseEntity {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 简答题目
     */
    private String name;

    /**
     * 简答详细描述
     */
    private String details;

    /**
     * 简答状态
     */
    private String status;

    /**
     * 节点id
     */
    private Long structId;

    /**
     * 删除标识
     */
    private String delFlag;

}
