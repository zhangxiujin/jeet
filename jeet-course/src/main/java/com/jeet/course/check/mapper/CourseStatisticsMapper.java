package com.jeet.course.check.mapper;

import com.jeet.course.check.vo.StatisticsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @PackageName : com.jeet.course.check.mapper
 * @FileName : CourseStatisticsMapper
 * @Description : 统计分析
 * @Author 李宇乐
 * @Date 2023/9/19 9:15
 * @Version : 1.0.0
 */
@Repository
public interface CourseStatisticsMapper {

    List<StatisticsVo> selectLearning(Long id);

}
