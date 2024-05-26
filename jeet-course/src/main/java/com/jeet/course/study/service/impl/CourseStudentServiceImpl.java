package com.jeet.course.study.service.impl;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.course.study.mapper.CourseStuCouMapper;
import com.jeet.course.study.service.ICourseStudentService;
import com.jeet.task.api.domain.CourseStuCou;
import com.jeet.task.api.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseStudentServiceImpl implements ICourseStudentService {
    @Autowired
    private CourseStuCouMapper courseStuCouMapper;

    @Override
    public void save(List<CourseStuCou> courseStuCouList) {
        if(courseStuCouList != null && courseStuCouList.size() > 0) {
            for (int i = 0; i < courseStuCouList.size(); i++) {
                courseStuCouList.get(i).setStuCoId(IdUtil.nextId());
            }
            courseStuCouMapper.insert(courseStuCouList);
        }
    }

    @Override
    public List<Long> queryCourseStuCouList(Long studentId) {
        return courseStuCouMapper.selectCourseStuCouList(studentId);
    }

    @Override
    public void removeStudentCourse(Long studentId) {
        courseStuCouMapper.delStudentCourse(studentId);
    }
}
