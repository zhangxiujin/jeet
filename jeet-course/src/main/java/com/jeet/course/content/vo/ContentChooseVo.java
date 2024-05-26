package com.jeet.course.content.vo;

import com.jeet.course.bank.domain.CourseChooseBank;
import lombok.Data;

/**
 * @Desc: ContentChooseVo
 * @author: xue
 * @Date: 2023/8/27 15:28
 */
@Data
public class ContentChooseVo extends CourseChooseBank {

    /**
     * 内容题库id
     */
    private Long contentBankId;

    /**
     * 所属节点名称
     */
    private String structName;



}
