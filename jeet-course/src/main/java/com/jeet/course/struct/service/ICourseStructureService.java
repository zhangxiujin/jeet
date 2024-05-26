package com.jeet.course.struct.service;

import com.jeet.course.struct.domain.CourseStructure;
import com.jeet.course.struct.vo.ListVo;
import com.jeet.task.api.vo.TreeVo;

import java.util.List;

public interface ICourseStructureService {

    /**
     * 新增课程结构
     * @param courseStructure
     */
    void saveCourseStructure(CourseStructure courseStructure);

    /**
     * 查询课程结构
     */
    List<CourseStructure> queryCourseStructureList(ListVo listVo);

    /**
     * 通过id查询课程结构
     * @param id
     * @return
     */
    CourseStructure queryCourseStructure(Long id);

    /**
     * 删除课程结构
     * @param id
     */
    void removeCourseStructure(Long id);

    /**
     * 更改课程结构
     * @param courseStructure
     */
    void modifyCourseStructure(CourseStructure courseStructure);

    /**
     * 批量删除
     * @param list
     */
    void removeCourseStructureList(List<Long> list);

    /**
     * 查询树结构
     */
    List<TreeVo> selectStructTree();

    /**
     * 查询所有课程名称
     */
    List<CourseStructure> selectAllCourse();

}
