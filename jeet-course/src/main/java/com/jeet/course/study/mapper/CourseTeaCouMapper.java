package com.jeet.course.study.mapper;

import com.jeet.task.api.domain.CourseTeaCou;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseTeaCouMapper {

    void insert(@Param(value = "courseTeaCouList") List<CourseTeaCou> courseTeaCouList);

    /**
     * 查询某个老师教授的课程
     * @param teacherId
     * @return
     */
    List<Long> selectCourseTeaCouList(Long teacherId);

    /**
     * 查询某个课程由哪些老师带
     * @param courseId
     * @return
     */
    List<Long> selectTeachers(Long courseId);

    void delTeacherCourse(Long teacherId);
}
