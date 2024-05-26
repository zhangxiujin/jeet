package com.jeet.course.personal.controller;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.course.personal.service.TestRecordService;
import com.jeet.course.personal.vo.BankErrorVo;
import com.jeet.course.personal.vo.QualifiedVo;
import com.jeet.course.personal.vo.TestRecordVo;
import com.jeet.course.study.domain.CoursePracticeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @PackageName : com.jeet.course.personal.testRecord.Controller
 * @FileName : testRecordController
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/7 15:09
 * @Version : 1.0.0
 */
@RestController
@RequestMapping("/testRecord")
public class TestRecordController extends BaseController {

    @Autowired
    private TestRecordService testRecordService;


    @GetMapping("queryTest")
    public TableDataInfo queryTest(TestRecordVo testRecordVo) {
        startPage();
        List<CoursePracticeRecord> coursePracticeRecordList = testRecordService.query(testRecordVo);
        return getDataTable(coursePracticeRecordList);
    }

    @GetMapping("queryTestId")
    public AjaxResult queryTestId(TestRecordVo testRecordVo) throws JsonProcessingException {
        List<BankErrorVo> select = testRecordService.select(testRecordVo);
        return AjaxResult.success(select);
    }

    /**
     * 查询练习是否合格
     * @return
     */
    @GetMapping("queryPracticeIfQualified")
    public AjaxResult queryPracticeIfQualified() {
        JSONObject jsonObject = testRecordService.queryPracticeIfQualified();
        return AjaxResult.success(jsonObject);
    }


}
