package com.jeet.course.study.controller;

import com.jeet.common.core.domain.R;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.course.study.service.ICourseStudentService;
import com.jeet.task.api.domain.CourseStuCou;
import com.jeet.task.api.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cour-stu")
public class CourseStudentController {
    @Autowired
    private ICourseStudentService courseStudentService;

    @PostMapping( "/saveStudentCourse")
    AjaxResult saveStudentCourse(@RequestBody List<CourseStuCou> courseStuCouList) {
        courseStudentService.save(courseStuCouList);
        return AjaxResult.success();
    }

    @GetMapping(value = "/queryCourseStuCouList/{studentId}")
    AjaxResult queryCourseStuCouList(@PathVariable("studentId") Long studentId) {
        List<Long> courseVos = courseStudentService.queryCourseStuCouList(studentId);
        return AjaxResult.success(courseVos);
    }

    @PostMapping(value = "/removeStudentCourse")
    AjaxResult removeStudentCourse(@RequestBody Long studentId) {
        courseStudentService.removeStudentCourse(studentId);
        return AjaxResult.success();
    }
}
