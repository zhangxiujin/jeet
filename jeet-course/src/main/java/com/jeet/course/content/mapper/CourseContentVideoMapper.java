package com.jeet.course.content.mapper;

import com.jeet.course.content.domain.CourseContentVideo;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseContentVideoMapper {

    /**
     * 新增
     */
    void insert(CourseContentVideo courseContentVideo);

    /**
     * 删除
     */
    void delete(Long id);

    /**
     * 查询
     */
    CourseContentVideo select(Long contentId);

    /**
     * 修改
     */
    void update(CourseContentVideo courseContentVideo);
}
