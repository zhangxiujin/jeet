package com.jeet.course.personal.service.impl;

import com.jeet.common.core.context.SecurityContextHolder;
import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.datasource.config.utils.PageUtils;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.content.domain.CourseContent;
import com.jeet.course.content.mapper.CourseContentMapper;
import com.jeet.course.personal.mapper.CourseStudyQuestionMapper;
import com.jeet.course.personal.vo.DoubtVo;
import com.jeet.course.personal.service.IDoubtService;
import com.jeet.course.study.domain.CourseStudyQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Desc: 答疑回复
 * @author: xue
 * @Date: 2023/9/27 17:16
 */
@Service
public class DoubtServiceImpl implements IDoubtService {

    @Autowired
    private CourseStudyQuestionMapper courseStudyQuestionMapper;

    @Autowired
    private CourseContentMapper courseContentMapper;

    @Override
    public List<DoubtVo> queryList(String status) {
        return courseStudyQuestionMapper.selectList(SecurityUtils.getUserId(), status);
    }

    @Override
    public Long saveQuestion(CourseStudyQuestion courseStudyQuestion) {
        long id = IdUtil.nextId();
        Long userId = SecurityContextHolder.getUserId();
        Date date = new Date(System.currentTimeMillis());
        courseStudyQuestion.setId(id);
        courseStudyQuestion.setCreateTime(date);
        courseStudyQuestion.setStatus("0");
        courseStudyQuestion.setUserId(userId);
        courseStudyQuestionMapper.insertQuestion(courseStudyQuestion);
        return id;
    }

    @Override
    public List<DoubtVo> queryDoubt(Long structId, String status) {
        CourseContent select = courseContentMapper.select(structId);
        if(select != null) {
            PageUtils.startPage();
            return courseStudyQuestionMapper.selectDoubt(select.getId(), SecurityUtils.getUserId(), status);
        }
        return new ArrayList<>();
    }
}
