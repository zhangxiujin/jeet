package com.jeet.course.struct.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Desc: CourseStructure
 * @author: 李宇乐
 * @Date: 2023/8/8 14:13
 */
@Getter
@Setter
public class CourseStructure extends BaseEntity {

    /**
     * 结构id
     */
    private Long structId;
    /**
     * 结构名称
     */
    @NotBlank(message = "结构名称不能为空")
    @Size(min = 1,max = 100,message = "结构名称长度不能超过100个字符")
    private String structName;
    /**
     * 结构类别
     */
    private String type;
    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer orderNum;
    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * 是否删除的标识
     */
    private String delFlag;

}
