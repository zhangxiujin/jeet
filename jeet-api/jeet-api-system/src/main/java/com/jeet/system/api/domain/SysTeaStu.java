package com.jeet.system.api.domain;

import lombok.Data;

/**
 * 老师学生关联
 * @author zhangxiujin
 */
@Data
public class SysTeaStu {
    private Long teaStuId;
    private Long teacherId;
    private Long studentId;
}
