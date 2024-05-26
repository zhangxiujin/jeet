package com.jeet.course.bank.vo;

import com.jeet.course.bank.domain.CourseBankAttachment;
import com.jeet.course.bank.domain.CourseProjectBank;
import lombok.Data;

import java.util.List;

/**
 * @Desc: ProjectStructVo
 * @author: xue
 * @Date: 2023/8/17 17:56
 */
@Data
public class ProjectStructVo extends CourseProjectBank {

    private String structName;

    private Long contentId;

    private List<CourseBankAttachment> attachList;

}
