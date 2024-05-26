package com.jeet.task.api.factory;

import com.jeet.common.core.domain.R;
import com.jeet.task.api.RemoteCourseService;
import com.jeet.task.api.domain.CourseStuCou;
import com.jeet.task.api.domain.CourseStudyRate;
import com.jeet.task.api.domain.CourseTeaCou;
import com.jeet.task.api.vo.CourseStudyRateVo;
import com.jeet.task.api.vo.CourseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Desc: RemoteTaskFallbackFactory
 * @author: xue
 * @Date: 2023/9/25 17:24
 */
public class RemoteCourseFallbackFactory implements FallbackFactory<RemoteCourseService> {

    private static final Logger log = LoggerFactory.getLogger(RemoteCourseService.class);

    @Override
    public RemoteCourseService create(Throwable cause) {
        log.error("定时任务服务调用失败:{}",cause.getMessage());
        return new RemoteCourseService()
        {
            @Override
            public R<CourseStudyRate> saveList(List<CourseStudyRateVo> sysTask) {
                return R.fail("保存失败");
            }

            @Override
            public R<CourseStudyRate> modifyList(List<CourseStudyRateVo> sysTask) {
                return R.fail("更新失败");
            }

            @Override
            public R<List<CourseStudyRate>> queryTaskList(Long id) {
                return R.fail("查询失败");
            }

            @Override
            public R<Void> saveStudentCourse(List<CourseStuCou> courseStuCouList) {
                return R.fail("保存失败");
            }

            @Override
            public R<List<Long>> queryCourseStuCouList(@PathVariable("studentId") Long studentId) {
                return R.fail("查询失败");
            }

            @Override
            public R<Void> removeStudentCourse(Long studentId) {
                return R.fail("删除失败");
            }

            @Override
            public R<Void> saveTeacherCourse(List<CourseTeaCou> courseTeaCouList) {
                return R.fail("保存失败");
            }

            @Override
            public R<List<Long>> queryCourseTeaCouList(Long teacherId) {
                return R.fail("查询失败");
            }

            @Override
            public R<Void> removeTeacherCourse(Long teacherId) {
                return R.fail("删除失败");
            }
        };
    }
}
