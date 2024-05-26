package com.jeet.task.api;

import com.jeet.common.core.constant.ServiceNameConstants;
import com.jeet.common.core.domain.R;
import com.jeet.task.api.domain.CourseStuCou;
import com.jeet.task.api.domain.CourseStudyRate;
import com.jeet.task.api.domain.CourseTeaCou;
import com.jeet.task.api.factory.RemoteCourseFallbackFactory;
import com.jeet.task.api.vo.CourseStudyRateVo;
import com.jeet.task.api.vo.CourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(contextId = "remoteCourseService", value = ServiceNameConstants.COURSE_SERVICE,
        fallbackFactory = RemoteCourseFallbackFactory.class)
public interface RemoteCourseService {

    @PostMapping(value = "/studyRate/saveStudyRateTaskList")
    R<CourseStudyRate> saveList(@RequestBody List<CourseStudyRateVo> sysTask);

    @PostMapping(value = "/studyRate/modifyStudyRateTaskList")
    R<CourseStudyRate> modifyList(@RequestBody List<CourseStudyRateVo> sysTask);

    @GetMapping(value = "/studyRate/queryStudyRateTaskList")
    R<List<CourseStudyRate>>  queryTaskList(Long id);

    @PostMapping(value = "/cour-stu/saveStudentCourse")
    R<Void> saveStudentCourse(List<CourseStuCou> courseStuCouList);

    @GetMapping(value = "/cour-stu/queryCourseStuCouList/{studentId}")
    R<List<Long>> queryCourseStuCouList(@PathVariable("studentId") Long studentId);

    @PostMapping(value = "/cour-stu/removeStudentCourse")
    R<Void> removeStudentCourse(Long studentId);

    @PostMapping(value = "/cour-tea/saveTeacherCourse")
    R<Void> saveTeacherCourse(List<CourseTeaCou> courseTeaCouList);

    @GetMapping(value = "/cour-tea/queryCourseTeaCouList/{teacherId}")
    R<List<Long>> queryCourseTeaCouList(@PathVariable("teacherId") Long teacherId);

    @PostMapping(value = "/cour-tea/removeTeacherCourse")
    R<Void> removeTeacherCourse(Long teacherId);
}
