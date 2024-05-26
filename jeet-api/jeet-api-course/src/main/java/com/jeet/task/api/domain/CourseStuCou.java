package com.jeet.task.api.domain;

import lombok.Data;

/**
 * 学生课程关联
 * @author zhangxiujin
 */
@Data
public class CourseStuCou {
    private Long StuCoId;
    private Long StuId;
    private Long CourseId;
}
