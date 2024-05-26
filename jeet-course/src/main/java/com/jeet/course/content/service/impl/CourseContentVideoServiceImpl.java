package com.jeet.course.content.service.impl;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.content.domain.CourseContentVideo;
import com.jeet.course.content.mapper.CourseContentVideoMapper;
import com.jeet.course.content.service.ICourseContentVideoService;
import com.jeet.course.content.vo.ContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Desc: CourseContentVideoServiceImpl
 * @author: xue
 * @Date: 2023/8/25 15:29
 */
@Service
public class CourseContentVideoServiceImpl implements ICourseContentVideoService {

    @Autowired
    private CourseContentVideoMapper courseContentVideoMapper;

    @Override
    public void save(ContentVo contentVo) {
        CourseContentVideo courseContentVideo = new CourseContentVideo();
        courseContentVideo.setId(contentVo.getVideoId());
        courseContentVideo.setName(contentVo.getVideoName());
        courseContentVideo.setExt(contentVo.getExt());
        courseContentVideo.setContentId(contentVo.getContentId());
        courseContentVideo.setUrl(contentVo.getUrl());
        courseContentVideo.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        courseContentVideo.setCreateTime(new Date(System.currentTimeMillis()));
        courseContentVideoMapper.insert(courseContentVideo);
    }

    @Override
    public CourseContentVideo query(Long contentId) {
        return courseContentVideoMapper.select(contentId);
    }

    @Override
    public void modify(ContentVo contentVo) {
        CourseContentVideo courseContentVideo = new CourseContentVideo();
        courseContentVideo.setContentId(contentVo.getContentId());
        courseContentVideo.setUrl(contentVo.getUrl());
        courseContentVideo.setName(contentVo.getVideoName());
        courseContentVideo.setExt(contentVo.getExt());
        courseContentVideoMapper.update(courseContentVideo);
    }

    @Override
    public void remove(Long id) {
        courseContentVideoMapper.delete(id);
    }
}
