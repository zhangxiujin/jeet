package com.jeet.course.check.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhangxiujin
 */
@Getter
@Setter
public class QueryPracticeRecordsVo {
    /**
     * 老师负责的课程id
     */
    private List<Long> courseIds;
    /**
     * 当前选中的课程结构id
     */
    private Long structId;
    /**
     * 当前选中的课程结构id所在的课程id
     */
    private Long courseId;
    /**
     * 老师负责的学生id
     */
    private List<Long> studentIds;
    /**
     * 当前选择查看记录的学生id
     */
    private Long userId;
    /**
     * 练习的审核状态
     */
    private String checkStatus;
    /**
     * 练习的题的类型
     */
    private String bankType;
    /**
     * 时间排序方式 asc|desc
     */
    private String sort = "asc";
}
