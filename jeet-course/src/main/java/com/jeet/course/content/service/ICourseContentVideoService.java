package com.jeet.course.content.service;

import com.jeet.course.content.domain.CourseContentVideo;
import com.jeet.course.content.vo.ContentVo;

public interface ICourseContentVideoService {

    /**
     * 新增
     * @param contentVo
     */
    void save(ContentVo contentVo);

    /**
     * 查询
     */
    CourseContentVideo query(Long contentId);

    /**
     * 修改
     */
    void modify(ContentVo contentVo);

    /**
     * 删除
     */
    void remove(Long id);
}
