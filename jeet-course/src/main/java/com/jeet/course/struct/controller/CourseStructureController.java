package com.jeet.course.struct.controller;

import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.course.struct.domain.CourseStructure;
import com.jeet.course.struct.service.ICourseStructureService;
import com.jeet.course.struct.vo.ListVo;
import com.jeet.task.api.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.jeet.common.datasource.config.utils.PageUtils.startPage;

/**
 * @Desc: CourseStructureController
 * @author: 李宇乐
 * @Date: 2023/8/9 9:13
 */
@RestController
@RequestMapping("/course-structure")
public class CourseStructureController extends BaseController {

    @Autowired
    private ICourseStructureService courseStructureService;

    @PostMapping("/save")
    public AjaxResult save(@RequestBody @Validated CourseStructure courseStructure) {
        courseStructureService.saveCourseStructure(courseStructure);
        return AjaxResult.success("保存成功");
    }

    @GetMapping("/list")
    public TableDataInfo list( ListVo listVo) {
        startPage();
        List<CourseStructure> list = courseStructureService.queryCourseStructureList(listVo);
        return getDataTable(list);
    }

    @GetMapping("/query")
    public AjaxResult query(Long id) {
        CourseStructure courseStructure = courseStructureService.queryCourseStructure(id);
        return AjaxResult.success(courseStructure);
    }

    @PostMapping("/remove")
    public AjaxResult remove(@RequestBody List<Long> ids) {
        if (ids.size() == 1) {
            courseStructureService.removeCourseStructure(ids.get(0));
        }else {
            courseStructureService.removeCourseStructureList(ids);
        }
        return AjaxResult.success("删除成功");
    }

    @PostMapping("/modify")
    public AjaxResult modify(@RequestBody @Validated CourseStructure courseStructure) {
        courseStructureService.modifyCourseStructure(courseStructure);
        return AjaxResult.success("更新成功");
    }

    @GetMapping("/selectStructTree")
    public AjaxResult selectStructTree() {
        List<TreeVo> treeVos = courseStructureService.selectStructTree();
        return AjaxResult.success(treeVos);
    }

    /**
     * 查询所有课程
     * @return
     */
    @GetMapping("/selectCourses")
    public AjaxResult selectCourses() {
        List<CourseStructure> courses = courseStructureService.selectAllCourse();
        return AjaxResult.success(courses);
    }

}
