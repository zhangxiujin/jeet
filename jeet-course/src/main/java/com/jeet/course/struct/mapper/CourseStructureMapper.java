package com.jeet.course.struct.mapper;

import com.jeet.course.struct.domain.CourseStructure;
import com.jeet.course.struct.vo.ListVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStructureMapper {

    /**
     * 条件查询
     * @param listVo
     * @return
     */
    List<CourseStructure> selectCourseStructList(ListVo listVo);

    /**
     * 新增课程结构
     * @param courseStructure 内容数据
     * @return
     */
    void insertCourseStructure(CourseStructure courseStructure);

    /**
     * 通过条件删除数据
     * @param id 删除条件
     * @return
     */
    void deleteCourseStructure(Long id);

    /**
     * 批量删除数据
     * @param list
     */
    void deleteCourseStructureList(List<Long> list);

    /**
     * 通过条件更改数据
     * @param courseStructure 更改的内容
     * @return
     */
    void updateCourseStructure(CourseStructure courseStructure);

    /**
     * 通过id查数据
     * @param id
     * @return
     */
    CourseStructure selectCourseStructure(Long id);

}
