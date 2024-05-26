package com.jeet.course.content.vo;

import com.jeet.course.bank.domain.CourseProjectBank;
import lombok.Data;

/**
 * @Desc: ContentProjectVo
 * @author: xue
 * @Date: 2023/8/27 15:28
 */
@Data
public class ContentProjectVo extends CourseProjectBank {

    /**
     * 内容题库id
     */
    private Long contentBankId;

    /**
     * 节点名称
     */
    private String structName;

}
