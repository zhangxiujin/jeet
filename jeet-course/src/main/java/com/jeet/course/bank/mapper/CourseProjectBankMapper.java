package com.jeet.course.bank.mapper;

import com.jeet.course.bank.domain.CourseProjectBank;
import com.jeet.course.bank.vo.ProjectBankVo;
import com.jeet.course.bank.vo.ProjectStructVo;
import com.jeet.course.bank.vo.ProjectVo;
import com.jeet.course.check.vo.BankAnswersVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @PackageName : com.jeet.course.bank.mapper
 * @FileName : CourseProjectBankMapper
 * @Description :
 * @Author 李宇乐
 * @Date 2023/8/17 19:02
 * @Version : 1.0.0
 */
@Repository
public interface CourseProjectBankMapper {

    void deleteProject(Long id);

    void deleteProjectList(List<Long> ids);

    /**
     * 新增数据
     * @param courseProjectBank
     */
    void insertProject(CourseProjectBank courseProjectBank);

    /**
     * 查询数据
     * @param projectVo
     * @return
     */
    List<ProjectStructVo> selectProject(ProjectVo projectVo);

    Integer selectCount(CourseProjectBank courseProjectBank);


    /**
     * 通过条件查询
     * @param id
     * @return
     */
    ProjectBankVo selectProjectId(Long id);

    /**
     * 查询项目信息和附件
     */
    ProjectBankVo selectProjectAndAttach(Long bankId);

    /**
     * 查询
     * @param courseProjectBank
     */
    void updateProject(CourseProjectBank courseProjectBank);

    /**
     * 查询未选择的题
     */
    List<ProjectStructVo> selectProjectByContentId(ProjectVo projectVo);

    /**
     * 查询多条数据
     */
    List<ProjectBankVo> selectProjectList(List<Long> list);

}
