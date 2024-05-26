package com.jeet.course.personal.service;

import com.jeet.course.personal.vo.DoubtVo;
import com.jeet.course.study.domain.CourseStudyQuestion;

import java.util.List;

public interface IDoubtService {

    /**
     * 查询所有信息
     * @param status 0未回复 1已回复
     */
    List<DoubtVo> queryList(String status);

    /**
     * 新增数据
     */
    Long saveQuestion(CourseStudyQuestion courseStudyQuestion);

    /**
     * 条件查询
     * @param structId 结构id
     * @param status 回复状态 1是 0否
     */
    List<DoubtVo> queryDoubt(Long structId, String status);

}
