package com.jeet.course.check.service;

import com.jeet.course.check.vo.StatisticsVo;

import java.util.List;

/**
 * @PackageName : com.jeet.course.check.service
 * @FileName : StatisticsService
 * @Description : 统计分析Service层
 * @Author 李宇乐
 * @Date 2023/9/19 9:13
 * @Version : 1.0.0
 */
public interface IStatisticsService {

    List<StatisticsVo> viewLearning(Long id);

}
