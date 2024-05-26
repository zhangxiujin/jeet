package com.jeet.course.struct.service.impl;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.struct.domain.CourseStructure;
import com.jeet.course.struct.mapper.CourseStructureMapper;
import com.jeet.course.struct.service.ICourseStructureService;
import com.jeet.course.struct.vo.ListVo;
import com.jeet.course.study.mapper.CourseStuCouMapper;
import com.jeet.course.study.mapper.CourseTeaCouMapper;
import com.jeet.system.api.constant.UserConstant;
import com.jeet.task.api.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Desc: CourseStructursServiceImpl
 * @author: 李宇乐
 * @Date: 2023/8/9 9:13
 */
@Service
public class CourseStructureServiceImpl implements ICourseStructureService {

    @Autowired
    private CourseStructureMapper courseStructureMapper;
    @Autowired
    private CourseTeaCouMapper courseTeaCouMapper;
    @Autowired
    private CourseStuCouMapper courseStuCouMapper;

    @Override
    public void saveCourseStructure(CourseStructure courseStructure) {
        courseStructure.setStructId(IdUtil.nextId());
        courseStructure.setCreateTime(new Date(System.currentTimeMillis()));
        courseStructure.setCreateBy("123123");
        courseStructure.setDelFlag("0");
        courseStructureMapper.insertCourseStructure(courseStructure);
    }

    @Override
    public List<CourseStructure> queryCourseStructureList(ListVo listVo) {
        return courseStructureMapper.selectCourseStructList(listVo);
    }

    @Override
    public CourseStructure queryCourseStructure(Long id) {
        return courseStructureMapper.selectCourseStructure(id);
    }

    @Override
    public void removeCourseStructure(Long id) {
        courseStructureMapper.deleteCourseStructure(id);
    }

    @Override
    public void modifyCourseStructure(CourseStructure courseStructure) {
        courseStructureMapper.updateCourseStructure(courseStructure);
    }

    @Override
    public void removeCourseStructureList(List<Long> list) {
        courseStructureMapper.deleteCourseStructureList(list);
    }

    @Override
    public List<TreeVo> selectStructTree() {
        ListVo listVo = new ListVo();
        List<CourseStructure> structs = courseStructureMapper.selectCourseStructList(listVo);
        List<TreeVo> trees = genTree(structs);
        if (SecurityUtils.getLoginUser().getSysUser()
                .getUserType().equals(UserConstant.USER_TYPE_TEACHER)) {
            List<Long> courseIds = courseTeaCouMapper.selectCourseTeaCouList(SecurityUtils.getUserId());
            if(courseIds != null) {
                trees = trees.stream().filter(r ->
                        courseIds.contains(r.getId())).collect(Collectors.toList());
            }
        } else if (SecurityUtils.getLoginUser().getSysUser()
                .getUserType().equals(UserConstant.USER_TYPE_STUDENT)) {
            List<Long> courseIds = courseStuCouMapper.selectCourseStuCouList(SecurityUtils.getUserId());
            if(courseIds != null) {
                trees = trees.stream().filter(r ->
                        courseIds.contains(r.getId())).collect(Collectors.toList());
            }
        }
        return trees;
    }


    @Override
    public List<CourseStructure> selectAllCourse() {
        ListVo listVo = new ListVo();
        listVo.setParentId(0L);
        List<CourseStructure> list = courseStructureMapper.selectCourseStructList(listVo);
        return list;
    }

    /**
     * 构造结构树
     * @param list
     * @return
     */
    private List<TreeVo> genTree(List<CourseStructure> list) {
        List<TreeVo> trees = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CourseStructure struct = list.get(i);
            if(struct.getParentId().equals(0L)) {
                TreeVo treeVo = new TreeVo();
                treeVo.setId(struct.getStructId());
                treeVo.setLabel(struct.getStructName());
                trees.add(treeVo);
            }
        }
        for (int i = 0; i < trees.size(); i++) {
            buildTree(list,trees.get(i));
        }
        return trees;
    }

    /**
     * 递归构造结构树
     * @param list
     * @param treeVo
     */
    private void buildTree(List<CourseStructure> list,TreeVo treeVo) {
        for (int i = 0; i < list.size(); i++) {
            CourseStructure struct = list.get(i);
            if (struct.getParentId().equals(treeVo.getId())) {
                TreeVo treeVo1 = new TreeVo();
                treeVo1.setId(struct.getStructId());
                treeVo1.setLabel(struct.getStructName());
                treeVo.getChildren().add(treeVo1);
                buildTree(list,treeVo1);
            }
        }
    }

}
