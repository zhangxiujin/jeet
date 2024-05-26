package com.jeet.course.study.mapper;

import com.jeet.task.api.domain.CourseStuCou;
import com.jeet.task.api.vo.CourseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStuCouMapper {

    /**
     * 保存学生课程关联信息
     * @param courseStuCouList
     */
    void insert(@Param(value = "courseStuCouList") List<CourseStuCou> courseStuCouList);

    /**
     * 通过学生id查询关联的课程
     * @param studentId
     * @return
     */
    List<Long> selectCourseStuCouList(Long studentId);

    /**
     * 删除学生和课程关系
     * @param studentId
     * @return
     */
    void delStudentCourse(Long studentId);
}
