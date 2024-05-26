package com.jeet.course.personal.mapper;

import com.jeet.course.personal.vo.StructureRateVo;
import com.jeet.task.api.domain.CourseStudyRate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalStudyRateMapper {

    /**
     * 新增学习进度
     * @param courseStudyRate
     */
    void insertStudyRate(CourseStudyRate courseStudyRate);

    /**
     * 查询学习进度
     * @param courseStudyRate
     * @return
     */
    List<CourseStudyRate> selectStudyRate(CourseStudyRate courseStudyRate);

    /**
     * 修改学习进度
     * @param courseStudyRate
     */
    void updateStudyRate(CourseStudyRate courseStudyRate);

    /**
     * 查询所有数据
     */
    List<StructureRateVo> selectList(Long userId);

    /**
     * 多条新增
     */
    void insertList(List<CourseStudyRate> list);

    /**
     * 修改多条数据
     */
    void updateList(List<CourseStudyRate> list);

    /**
     * 查询所有数据
     */
    List<CourseStudyRate> selectTaskList();

}
