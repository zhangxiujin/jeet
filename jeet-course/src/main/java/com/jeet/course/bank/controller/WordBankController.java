package com.jeet.course.bank.controller;

import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.course.bank.domain.CourseWordBank;
import com.jeet.course.bank.service.ICourseWordBankService;
import com.jeet.course.bank.vo.BankStructureVo;
import com.jeet.course.bank.vo.QueryListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Desc: 题库管理控制器
 * @author: 李宇乐
 * @Date: 2023/8/14 9:43
 */
@RestController
@RequestMapping("/wordBank")
public class WordBankController extends BaseController {

    @Autowired
    private ICourseWordBankService courseWordBankService;

    @PostMapping("/saveWord")
    public AjaxResult saveWord(@RequestBody CourseWordBank courseWordBank) {
        courseWordBankService.saveWord(courseWordBank);
        return AjaxResult.success();
    }

    @GetMapping("/listWord")
    public TableDataInfo listWord(QueryListVo queryListVo) {
        startPage();
        List<BankStructureVo> words = courseWordBankService.queryWordList(queryListVo);
        return getDataTable(words);
    }

    @GetMapping("/queryWord")
    public AjaxResult queryWord(Long id) {
        CourseWordBank courseWordBank = courseWordBankService.queryWord(id);
        return AjaxResult.success(courseWordBank);
    }

    @PostMapping("/modifyWord")
    public AjaxResult modifyWord(@RequestBody @Validated CourseWordBank courseWordBank) {
        courseWordBankService.modifyWord(courseWordBank);
        return AjaxResult.success("更新成功");
    }

    @PostMapping("/deleteWord")
    public AjaxResult deleteWord(@RequestBody List<Long> ids) {
        if (ids.size() == 1) {
            courseWordBankService.deleteWord(ids.get(0));
        }else {
            courseWordBankService.deleteWords(ids);
        }
        return AjaxResult.success("删除成功");
    }

    @PostMapping("/changeWordStatus")
    public AjaxResult changeWordStatus(@RequestBody CourseWordBank courseWordBank) {
        courseWordBankService.modifyWord(courseWordBank);
        return AjaxResult.success("修改成功");
    }

    /**
     * 查询未绑定某个课程内容的单词题库
     */
    @GetMapping("/listUnbindWord")
    public TableDataInfo listUnbindWord(QueryListVo queryListVo) {
        startPage();
        List<BankStructureVo> bankStructureVos = courseWordBankService.queryWordByContentId(queryListVo);
        return getDataTable(bankStructureVos);
    }

}
