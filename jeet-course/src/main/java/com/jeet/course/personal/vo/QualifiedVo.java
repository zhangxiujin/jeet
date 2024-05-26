package com.jeet.course.personal.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询练习合格
 */
@Getter
@Setter
public class QualifiedVo {
    /**
     * 课程结构id
     */
    private Long StructId;
    /**
     * 是否合格
     */
    private Boolean qualified;
}
