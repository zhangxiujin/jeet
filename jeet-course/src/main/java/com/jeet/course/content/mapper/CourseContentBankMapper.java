package com.jeet.course.content.mapper;

import com.jeet.course.content.domain.CourseContentBank;
import com.jeet.course.content.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseContentBankMapper {

    /**
     * 新增
     */
    void insert(CourseContentBank courseContentBank);

    /**
     * 批量新增
     */
    void insertList(List<CourseContentBank> list);

    /**
     * 查询单词题库
     */
    List<ContentWordVo> selectWord(Long contentId);

    /**
     * 查询选择题库
     */
    List<ContentChooseVo> selectChoose(Long contentId);

    /**
     * 查询项目题库
     */
    List<ContentProjectVo> selectProject(Long contentId);

    /**
     * 查询简答题库
     */
    List<ContentSimpleVo> selectSimple(Long contentId);

    /**
     * 删除选中的单词数据
     */
    void deleteWord(DeleteWordVo deleteWordVo);

    /**
     * 删除选中的选择数据
     */
    void deleteChoose(DeleteChooseVo deleteChooseVo);

    /**
     * 删除选中的项目数据
     */
    void deleteProject(DeleteProjectVo deleteProjectVo);

    /**
     * 删除选中的简答数据
     */
    void deleteSimple(DeleteSimpleVo deleteSimpleVo);


}
