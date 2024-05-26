package com.jeet.course.bank.service;

import com.jeet.course.bank.domain.CourseChooseBank;
import com.jeet.course.bank.domain.CourseProjectBank;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.bank.vo.ProjectBankVo;
import com.jeet.course.bank.vo.ProjectStructVo;
import com.jeet.course.bank.vo.ProjectVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PackageName : com.jeet.course.bank.service
 * @FileName : IcourseProjectBankService
 * @Description :
 * @Author 李宇乐
 * @Date 2023/8/17 18:49
 * @Version : 1.0.0
 */

public interface ICourseProjectBankService {

    /**
     * 单个删除
     * @param id
     */
    void removeProject(Long id);

    /**
     * 多个删除
     * @param ids
     */
    void removeProjectList(List<Long> ids);

    /**
     * 新增方法
     * @param courseProjectBank
     */
    Long saveProject(CourseProjectBank courseProjectBank);

    /**
     * 查询方法
     * @param projectVo
     * @return
     */
    List<ProjectStructVo> queryProject(ProjectVo projectVo);

    /**
     * 通过id查询
     */
    ProjectBankVo queryProjectId(Long id);

    /**
     * 查看项目题详情
     * @param bankId
     * @return
     */
    ProjectBankVo queryProjectDetail(Long bankId);

    /**
     * 更新选择题库数据
     */
    void modifyProject(CourseProjectBank courseProjectBank);

    /**
     * 查询未被选择的题目
     */
    List<ProjectStructVo> queryProjectByContentId(ProjectVo projectVo);

}
