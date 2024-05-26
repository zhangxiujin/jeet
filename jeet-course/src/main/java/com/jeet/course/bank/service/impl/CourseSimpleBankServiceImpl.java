package com.jeet.course.bank.service.impl;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.course.bank.domain.CourseSimpleBank;
import com.jeet.course.bank.mapper.CourseSimpleBankMapper;
import com.jeet.course.bank.service.ICourseSimpleBankService;
import com.jeet.course.bank.vo.SimpleBankVo;
import com.jeet.course.bank.vo.SimpleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Desc: CourseSimpleBankServiceImpl
 * @author: xue
 * @Date: 2023/9/2 23:17
 */
@Service
public class CourseSimpleBankServiceImpl implements ICourseSimpleBankService {

    @Autowired
    private CourseSimpleBankMapper courseSimpleBankMapper;

    @Override
    public void save(CourseSimpleBank courseSimpleBank) {
        courseSimpleBank.setId(IdUtil.nextId());
        courseSimpleBank.setCreateTime(new Date(System.currentTimeMillis()));
        courseSimpleBank.setDelFlag("0");
        courseSimpleBankMapper.insert(courseSimpleBank);
    }

    @Override
    public List<SimpleVo> query(SimpleBankVo simpleBankVo) {
        return courseSimpleBankMapper.select(simpleBankVo);
    }

    @Override
    public SimpleVo queryById(Long id) {
        return courseSimpleBankMapper.selectById(id);
    }

    @Override
    public void modify(CourseSimpleBank courseSimpleBank) {
        courseSimpleBankMapper.update(courseSimpleBank);
    }

    @Override
    public void removeById(Long id) {
        courseSimpleBankMapper.deleteById(id);
    }

    @Override
    public void removeList(List<Long> ids) {
        courseSimpleBankMapper.deleteList(ids);
    }

    @Override
    public List<SimpleVo> queryByContentId(SimpleBankVo simpleBankVo) {
        return courseSimpleBankMapper.selectByContentId(simpleBankVo);
    }
}
