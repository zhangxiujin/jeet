package com.jeet.task.api.domain;

import lombok.Data;

/**
 * @author zhangxiujin
 * 老师课程关联
 */
@Data
public class CourseTeaCou {
    private Long teaCoId;
    private Long teacherId;
    private Long courseId;
}
