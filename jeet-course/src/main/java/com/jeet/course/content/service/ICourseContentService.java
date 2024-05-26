package com.jeet.course.content.service;

import com.jeet.course.bank.domain.CourseWordBank;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.content.vo.ContentVo;
import com.jeet.course.content.vo.CourseContentVo;

import javax.swing.text.AbstractDocument;
import java.util.List;

/**
 * 课程内容服务接口
 */
public interface ICourseContentService {

    /**
     * 新增
     * @param contentVo
     * @return
     */
    Long save(ContentVo contentVo);

    /**
     * 查询
     */
    CourseContent query(Long structId);

    /**
     * 修改
     */
    void modify(ContentVo contentVo);

    /**
     * 查询内容和视频
     */
    ContentVo queryContentAndVideo(Long structId);

    /**
     * 查询某一节内容下的单词练习题
     */
    List<CourseWordBank> queryWordListByContentId(Long contentId);

}
