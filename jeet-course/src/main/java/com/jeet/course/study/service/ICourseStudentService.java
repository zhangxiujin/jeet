package com.jeet.course.study.service;

import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.task.api.domain.CourseStuCou;
import com.jeet.task.api.vo.CourseVo;

import java.util.List;

/**
 * 课程学生关联服务
 */
public interface ICourseStudentService {

    /**
     * 保存学生课程关联信息
     * @param courseStuCouList
     */
    void save(List<CourseStuCou> courseStuCouList);

    /**
     * 查询某个学生学习的课程
     */
    List<Long> queryCourseStuCouList(Long studentId);

    /**
     * 删除学生和课程关系
     * @param studentId
     * @return
     */
    void removeStudentCourse(Long studentId);
}
