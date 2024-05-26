package com.jeet.course.content.mapper;

import com.jeet.course.bank.domain.CourseWordBank;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.content.vo.ContentVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseContentMapper {

    /**
     * 新增
     * @return
     */
    void insert(CourseContent courseContent);

    /**
     * 修改
     */
    void update(CourseContent courseContent);

    /**
     * 查询
     */
    CourseContent select(Long structId);

    /**
     * 查询内容和视频
     */
    ContentVo selectContentAndVideo(Long structId);

    /**
     * 通过内容id查询单词
     */
    List<CourseWordBank> selectWordListByContentId(Long contentId);
}
