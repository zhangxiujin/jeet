package com.jeet.course.check.controller;

import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.course.check.service.IStatisticsService;
import com.jeet.course.check.vo.StatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @PackageName : com.jeet.course.check.controller
 * @FileName : StatisticsController
 * @Description : 统计分析控制器
 * @Author 李宇乐
 * @Date 2023/9/19 9:08
 * @Version : 1.0.0
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {


    @Autowired
    private IStatisticsService statisticsService;


    @GetMapping("/queryLearning")
    public AjaxResult queryLearning(Long id){
        List<StatisticsVo> view = statisticsService.viewLearning(id);
        return AjaxResult.success(view);
    }


}
