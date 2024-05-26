package com.jeet.course.personal.controller;

import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.course.personal.service.IPersonalStudyRateService;
import com.jeet.course.personal.vo.RateVo;
import com.jeet.task.api.domain.CourseStudyRate;
import com.jeet.task.api.vo.CourseStudyRateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Desc: PersonalStudyRateController
 * @author: xue
 * @Date: 2023/9/14 16:58
 */
@RestController
@RequestMapping("/studyRate")
public class PersonalStudyRateController {

    @Autowired
    private IPersonalStudyRateService personalStudyRateService;

    @PostMapping("/saveStudyRate")
    public AjaxResult saveStudyRate(@RequestBody CourseStudyRateVo courseStudyRateVo) {
        Map<String, Object> map = personalStudyRateService.saveStudyRate(courseStudyRateVo);
        return AjaxResult.success(map);
    }

    @GetMapping("/queryStudyRate")
    public AjaxResult queryStudyRate(CourseStudyRate courseStudyRate) {
        List<CourseStudyRate> courseStudyRates =
                personalStudyRateService.queryStudyRate(courseStudyRate);
        return AjaxResult.success(courseStudyRates);
    }

    @PostMapping("/modifyStudyRate")
    public AjaxResult modifyStudyRate(CourseStudyRate courseStudyRate) {
        personalStudyRateService.modifyStudyRate(courseStudyRate);
        return AjaxResult.success("修改成功");
    }

    /**
     * 用户自己查看自己的学习进度树
     * @param userId
     * @return
     */
    @GetMapping("/queryList")
    public AjaxResult queryList(Long userId) {
        List<RateVo> rateVos = personalStudyRateService.queryList(userId);
        return AjaxResult.success(rateVos);
    }

    @PostMapping("/saveStudyRateTaskList")
    public AjaxResult saveList(@RequestBody List<CourseStudyRateVo> list) {
        personalStudyRateService.saveList(list);
        return AjaxResult.success();
    }

    @PostMapping("/modifyStudyRateTaskList")
    public AjaxResult modifyList(@RequestBody List<CourseStudyRateVo> list) {
        personalStudyRateService.modifyList(list);
        return AjaxResult.success();
    }

    @GetMapping("/queryStudyRateTaskList")
    public AjaxResult queryTaskList(Long id) {
        List<CourseStudyRate> courseStudyRates = personalStudyRateService.queryTaskList(id);
        return AjaxResult.success(courseStudyRates);
    }

}
