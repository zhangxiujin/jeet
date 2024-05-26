package com.jeet.course.study.service;

import com.jeet.task.api.domain.CourseTeaCou;

import java.util.List;

public interface ICourseTeacherService {

    /**
     * 保存老师课程关联信息
     * @param courseTeaCouList
     */
    void save(List<CourseTeaCou> courseTeaCouList);

    /**
     * 查询某个老师教授的课程
     */
    List<Long> queryCourseTeaCouList(Long teacherId);

    /**
     * 查询某个课程被哪些老师带
     */
    List<Long> queryTeachers(Long courseId);

    /**
     * 删除老师和课程关系
     * @param teacherId
     * @return
     */
    void removeTeacherCourse(Long teacherId);
}
