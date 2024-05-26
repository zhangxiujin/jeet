package com.jeet.course.bank.controller;

import com.jeet.common.core.web.domain.AjaxResult;

import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.course.bank.domain.CourseChooseBank;
import com.jeet.course.bank.service.ICourseChooseBankService;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.bank.vo.ChooseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Desc: ChooseController
 * @author: 李宇乐
 * @Date: 2023/8/16 10:33
 */
@RestController
@RequestMapping("/choose")
public class ChooseController extends BaseController {

    @Autowired
    private ICourseChooseBankService courseChooseBankService;

    @PostMapping("/save")
    public AjaxResult saveCourseChooseBank(@RequestBody CourseChooseBank courseChooseBank) {
        courseChooseBankService.saveCourseChooseBank(courseChooseBank);
        return AjaxResult.success("新增成功");
    }

    @GetMapping("/queryChoose")
    public TableDataInfo queryChoose(ChooseVo chooseVo) {
        startPage();
        List<ChooseStructureVo> choose = courseChooseBankService.queryChoose(chooseVo);
        return getDataTable(choose);
    }

    @PostMapping("/remove")
    public AjaxResult removeChoose(@RequestBody List<Long> ids) {
        if (ids.size() == 1) {
            courseChooseBankService.removeChoose(ids.get(0));
        }else {
            courseChooseBankService.removeChooseList(ids);
        }
        return AjaxResult.success("删除成功");
    }

    @GetMapping("/queryChooseId")
    public AjaxResult queryChooseList(Long id) {
        ChooseStructureVo courseChooseBank = courseChooseBankService.queryChooseId(id);
        return AjaxResult.success(courseChooseBank);
    }

    @PostMapping("/modifyChoose")
    public AjaxResult modifyChoose(@RequestBody CourseChooseBank courseChooseBank) {
        courseChooseBankService.modifyChoose(courseChooseBank);
        return AjaxResult.success("更新成功");
    }

    @PostMapping("/modifyChooseStatus")
    public AjaxResult modifyChooseStatus(@RequestBody CourseChooseBank courseChooseBank) {
        courseChooseBankService.modifyChoose(courseChooseBank);
        return AjaxResult.success("状态更新成功");
    }

    @GetMapping("/listUnbindChoose")
    public TableDataInfo listUnbindChoose(ChooseVo chooseVo) {
        startPage();
        List<ChooseStructureVo> chooseStructureVos =
                courseChooseBankService.queryChooseByContentId(chooseVo);
        return getDataTable(chooseStructureVos);
    }

}
