package com.jeet.course.content.service.impl;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.bank.domain.CourseWordBank;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.content.mapper.CourseContentMapper;
import com.jeet.course.content.service.ICourseContentService;
import com.jeet.course.content.vo.ContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Desc: CourseContentServiceImpl
 * @author: xue
 * @Date: 2023/8/24 15:17
 */
@Service
public class CourseContentServiceImpl implements ICourseContentService {

    @Autowired
    private CourseContentMapper courseContentMapper;

    @Override
    public Long save(ContentVo contentVo) {
        CourseContent courseContent = new CourseContent();
        Long id = IdUtil.nextId();
        courseContent.setId(id);
        courseContent.setName(contentVo.getName());
        courseContent.setDesc(contentVo.getDesc());
        courseContent.setStructId(contentVo.getStructId());
        courseContent.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        courseContent.setCreateTime(new Date(System.currentTimeMillis()));
        courseContentMapper.insert(courseContent);
        return id;
    }

    @Override
    public CourseContent query(Long structId) {
        return courseContentMapper.select(structId);
    }

    @Override
    public void modify(ContentVo contentVo) {
        CourseContent courseContent = new CourseContent();
        courseContent.setDesc(contentVo.getDesc());
        courseContent.setStructId(contentVo.getStructId());
        courseContentMapper.update(courseContent);
    }

    @Override
    public ContentVo queryContentAndVideo(Long structId) {
        return courseContentMapper.selectContentAndVideo(structId);
    }

    @Override
    public List<CourseWordBank> queryWordListByContentId(Long contentId) {
        return courseContentMapper.selectWordListByContentId(contentId);
    }
}
