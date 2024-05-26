package com.jeet.course.bank.service.impl;

import com.jeet.course.bank.mapper.CourseProjectBankMapper;
import com.jeet.course.bank.service.ICourseProjectBankService;
import com.jeet.common.core.context.SecurityContextHolder;
import com.jeet.common.core.utils.IdUtil;
import com.jeet.course.bank.domain.CourseProjectBank;
import com.jeet.course.bank.mapper.CourseProjectBankMapper;
import com.jeet.course.bank.service.ICourseProjectBankService;
import com.jeet.course.bank.vo.ProjectBankVo;
import com.jeet.course.bank.vo.ProjectStructVo;
import com.jeet.course.bank.vo.ProjectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @PackageName : com.jeet.course.bank.service.impl
 * @FileName : CourseProjectServiceImpl
 * @Description :
 * @Author 李宇乐
 * @Date 2023/8/17 19:01
 * @Version : 1.0.0
 */
@Service
public class CourseProjectBankServiceImpl implements ICourseProjectBankService {

    @Autowired
    private CourseProjectBankMapper courseProjectBankMapper;

    @Override
    public void removeProject(Long id) {
        courseProjectBankMapper.deleteProject(id);
    }

    @Override
    public void removeProjectList(List<Long> ids) {
        courseProjectBankMapper.deleteProjectList(ids);
    }

    @Override
    public Long saveProject(CourseProjectBank courseProjectBank) {
        courseProjectBank.setDelFlag("0");
        Integer integer = courseProjectBankMapper.selectCount(courseProjectBank);
        if (integer > 0) {
            throw new SecurityException("项目已存在");
        }
        long id = IdUtil.nextId();
        courseProjectBank.setId(id);
        Long userId = SecurityContextHolder.getUserId();
        courseProjectBank.setCreateBy(String.valueOf(userId));
        courseProjectBank.setCreateTime(new Date(System.currentTimeMillis()));
        courseProjectBankMapper.insertProject(courseProjectBank);
        return id;
    }

    @Override
    public List<ProjectStructVo> queryProject(ProjectVo projectVo) {
        return courseProjectBankMapper.selectProject(projectVo);
    }

    @Override
    public ProjectBankVo queryProjectId(Long id) {
        ProjectBankVo projectBankVo = courseProjectBankMapper.selectProjectId(id);
        return projectBankVo;
    }

    @Override
    public ProjectBankVo queryProjectDetail(Long bankId) {
        return courseProjectBankMapper.selectProjectAndAttach(bankId);
    }

    @Override
    public void modifyProject(CourseProjectBank courseProjectBank) {
        courseProjectBankMapper.updateProject(courseProjectBank);
    }

    @Override
    public List<ProjectStructVo> queryProjectByContentId(ProjectVo projectVo) {
        return courseProjectBankMapper.selectProjectByContentId(projectVo);
    }
}
