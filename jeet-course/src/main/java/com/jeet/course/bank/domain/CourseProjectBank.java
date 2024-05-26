package com.jeet.course.bank.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @PackageName : com.jeet.course.bank.domain
 * @FileName : CourseProjectBank
 * @Description : 项目题库实体类
 * @Author 李宇乐
 * @Date 2023/8/17 18:00
 * @Version : 1.0.0
 */
@Data
public class CourseProjectBank extends BaseEntity {
    /**
     * id
     */
    private Long id;
    /**
     * 题目
     */
    private String name;
    /**
     * 状态
     */
    private String status;
    /**
     * 结构id
     */
    private Long structId;
    /**
     * 详情
     */
    private String details;
    /**
     * 删除标识
     */
    private String delFlag;
    /**
     * 备注
     */
    private String remark;

}
