package com.jeet.course.study.service.impl;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.course.study.mapper.CourseStuCouMapper;
import com.jeet.course.study.mapper.CourseTeaCouMapper;
import com.jeet.course.study.service.ICourseTeacherService;
import com.jeet.task.api.domain.CourseTeaCou;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTeacherServiceImpl implements ICourseTeacherService {
    @Autowired
    private CourseTeaCouMapper courseTeaCouMapper;

    @Override
    public void save(List<CourseTeaCou> courseTeaCouList) {
        if(courseTeaCouList != null && courseTeaCouList.size() > 0) {
            for (int i = 0; i < courseTeaCouList.size(); i++) {
                courseTeaCouList.get(i).setTeaCoId(IdUtil.nextId());
            }
            courseTeaCouMapper.insert(courseTeaCouList);
        }
    }

    @Override
    public List<Long> queryCourseTeaCouList(Long teacherId) {
        return courseTeaCouMapper.selectCourseTeaCouList(teacherId);
    }

    @Override
    public List<Long> queryTeachers(Long courseId) {
        return courseTeaCouMapper.selectTeachers(courseId);
    }

    @Override
    public void removeTeacherCourse(Long teacherId) {
        courseTeaCouMapper.delTeacherCourse(teacherId);
    }
}
