package com.jeet.course.bank.controller;

import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.course.bank.domain.CourseSimpleBank;
import com.jeet.course.bank.service.ICourseSimpleBankService;
import com.jeet.course.bank.vo.SimpleBankVo;
import com.jeet.course.bank.vo.SimpleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Desc: SimpleController
 * @author: xue
 * @Date: 2023/9/2 23:19
 */
@RestController
@RequestMapping("/simple")
public class SimpleController extends BaseController {

    @Autowired
    private ICourseSimpleBankService courseSimpleBankService;

    @PostMapping("/save")
    public AjaxResult save(@RequestBody CourseSimpleBank courseSimpleBank) {
        courseSimpleBankService.save(courseSimpleBank);
        return AjaxResult.success("新增成功");
    }

    @GetMapping("/query")
    public TableDataInfo query(SimpleBankVo simpleBankVo) {
        List<SimpleVo> simpleVoList = courseSimpleBankService.query(simpleBankVo);
        return getDataTable(simpleVoList);
    }

    @GetMapping("/queryById")
    public AjaxResult queryById(Long id) {
        SimpleVo simpleVo = courseSimpleBankService.queryById(id);
        return AjaxResult.success(simpleVo);
    }

    @PostMapping("/modify")
    public AjaxResult modify(@RequestBody CourseSimpleBank courseSimpleBank) {
        courseSimpleBankService.modify(courseSimpleBank);
        return AjaxResult.success("修改成功");
    }

    @PostMapping("/remove")
    public AjaxResult removeSimple(@RequestBody List<Long> ids) {
        if (ids.size() == 1) {
            courseSimpleBankService.removeById(ids.get(0));
        }else {
            courseSimpleBankService.removeList(ids);
        }
        return AjaxResult.success("删除成功");
    }

    @PostMapping("/modifyStatus")
    public AjaxResult modifySimpleStatus(@RequestBody CourseSimpleBank courseSimpleBank) {
        courseSimpleBankService.modify(courseSimpleBank);
        return AjaxResult.success("修改成功");
    }

    /**
     * 查询未绑定某个课程的简答题
     */
    @GetMapping("/listUnbindSimple")
    public TableDataInfo listUnbindSimple(SimpleBankVo simpleBankVo) {
        startPage();
        List<SimpleVo> simpleVos = courseSimpleBankService.queryByContentId(simpleBankVo);
        return getDataTable(simpleVos);
    }

}
