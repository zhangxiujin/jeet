package com.jeet.course.personal.service;

import com.jeet.course.personal.vo.RateVo;
import com.jeet.task.api.domain.CourseStudyRate;
import com.jeet.task.api.vo.CourseStudyRateVo;

import java.util.List;
import java.util.Map;

public interface IPersonalStudyRateService {

    /**
     * 新增学习进度
     */
    Map<String, Object> saveStudyRate(CourseStudyRateVo courseStudyRateVo);

    /**
     * 查询学习进度
     */
    List<CourseStudyRate> queryStudyRate(CourseStudyRate courseStudyRate);

    /**
     * 修改学习进度
     */
    void modifyStudyRate(CourseStudyRate courseStudyRate);

    /**
     * 查询所有数据
     */
    List<RateVo> queryList(Long userId);

    /**
     * 新增多条数据
     */
    void saveList(List<CourseStudyRateVo> list);

    /**
     * 批量更新
     */
    void modifyList(List<CourseStudyRateVo> list);

    /**
     * 查询所有数据
     */
    List<CourseStudyRate> queryTaskList(Long id);

}
