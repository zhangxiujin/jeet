package com.jeet.course.check.service.impl;

import com.jeet.course.check.mapper.CourseStatisticsMapper;
import com.jeet.course.check.service.IStatisticsService;
import com.jeet.course.check.vo.StatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName : com.jeet.course.check.service.impl
 * @FileName : statisticsService
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/19 9:12
 * @Version : 1.0.0
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    private CourseStatisticsMapper statisticsMapper;

    @Override
    public List<StatisticsVo> viewLearning(Long id) {
        List<StatisticsVo> statisticsVos = statisticsMapper.selectLearning(id);
        return statisticsVos;
    }
}
