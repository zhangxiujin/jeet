package com.jeet.course.content.vo;

import com.jeet.course.bank.domain.CourseSimpleBank;
import lombok.Data;

/**
 * @Desc: ContentSimpleVo
 * @author: xue
 * @Date: 2023/9/4 11:54
 */
@Data
public class ContentSimpleVo extends CourseSimpleBank {

    /**
     * 内容题库id
     */
    private Long contentBankId;

    /**
     * 节点名称
     */
    private String structName;

}
