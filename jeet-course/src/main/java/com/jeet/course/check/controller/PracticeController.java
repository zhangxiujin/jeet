package com.jeet.course.check.controller;

import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.course.check.service.ICheckPracticeService;
import com.jeet.course.check.vo.*;
import com.jeet.course.study.vo.WordContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Desc: Practice
 * @author: xue
 * @Date: 2023/9/7 15:05
 */
@RestController
@RequestMapping("/practice")
public class PracticeController extends BaseController {

    @Autowired
    private ICheckPracticeService checkPracticeService;

    @GetMapping("/queryPracticeUser")
    public TableDataInfo queryPracticeUser(PracticeVo practiceVo) {
        startPage();
        List<PracticeUserVo> practiceUserVos = checkPracticeService.queryPracticeUser(practiceVo);
        return getDataTable(practiceUserVos);
    }

    @GetMapping("/queryPractice")
    public TableDataInfo queryPractice(PracticeVo practiceVo) {
        startPage();
        List<PracticeRecordVo> practiceRecordVos =
                checkPracticeService.queryPractice(practiceVo);
        return getDataTable(practiceRecordVos);
    }

    /**
     * 查询未审核的练习
     * @return
     */
    @GetMapping("/queryUnCheckPractice")
    public TableDataInfo queryUnCheckPractice(QueryPracticeRecordsVo queryPracticeRecordsVo) {
        List<PracticeRecordVo> practiceRecordVos =
                checkPracticeService.queryUnCheckPractice(queryPracticeRecordsVo);
        return getDataTable(practiceRecordVos);
    }

    /**
     * 查询已审核的练习
     * @param queryPracticeRecordsVo
     * @return
     */
    @GetMapping("/queryCheckedPractice")
    public TableDataInfo queryCheckedPractice(QueryPracticeRecordsVo queryPracticeRecordsVo) {
        List<PracticeRecordVo> practiceRecordVos =
                checkPracticeService.queryCheckedPractice(queryPracticeRecordsVo);
        return getDataTable(practiceRecordVos);
    }

    @GetMapping("/queryPracticeDetails")
    public AjaxResult queryPracticeDetails(PracticeVo practiceVo) {
        Object obj = checkPracticeService.queryPracticeDetails(practiceVo);
        return AjaxResult.success(obj);
    }

    @PostMapping("/modifyWordPractice")
    public AjaxResult modifyWordPractice(@RequestBody WordContentVo wordContentVo) {
        checkPracticeService.modifyWord(wordContentVo);
        return AjaxResult.success("单词审核成功");
    }

    @PostMapping("/modifySimplePractice")
    public AjaxResult modifySimplePractice(@RequestBody PracticeProjectVo practiceProjectVo) {
        checkPracticeService.modifySimple(practiceProjectVo);
        return AjaxResult.success("简答审核成功");
    }

    @PostMapping("/modifyProjectPractice")
    public AjaxResult modifyProjectPractice(@RequestBody PracticeProjectVo practiceProjectVo) {
        checkPracticeService.modifyProject(practiceProjectVo);
        return AjaxResult.success("项目审核完成");
    }

    @PostMapping("/modifyChoosePractice")
    public AjaxResult modifyChoosePractice(@RequestBody ChoosePracticeVo choosePracticeVo) {
        checkPracticeService.modifyChoose(choosePracticeVo);
        return AjaxResult.success("简答审核完成");
    }

}
