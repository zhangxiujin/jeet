package com.jeet.course.study.controller;

import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.course.study.service.ICourseTeacherService;
import com.jeet.task.api.domain.CourseTeaCou;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cour-tea")
public class CourseTeacherController {

    @Autowired
    private ICourseTeacherService courseTeacherService;

    @PostMapping( "/saveTeacherCourse")
    AjaxResult saveTeacherCourse(@RequestBody List<CourseTeaCou> courseTeaCouList) {
        courseTeacherService.save(courseTeaCouList);
        return AjaxResult.success();
    }

    @GetMapping(value = "/queryCourseTeaCouList/{teacherId}")
    AjaxResult queryCourseTeaCouList(@PathVariable("teacherId") Long teacherId) {
        List<Long> courseVos = courseTeacherService.queryCourseTeaCouList(teacherId);
        return AjaxResult.success(courseVos);
    }

    @PostMapping(value = "/removeTeacherCourse")
    AjaxResult removeTeacherCourse(@RequestBody Long teacherId) {
        courseTeacherService.removeTeacherCourse(teacherId);
        return AjaxResult.success();
    }
}
