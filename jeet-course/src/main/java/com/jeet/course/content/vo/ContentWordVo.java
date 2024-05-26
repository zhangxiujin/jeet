package com.jeet.course.content.vo;

import com.jeet.course.bank.domain.CourseWordBank;
import lombok.Data;

/**
 * @Desc: ContentWordVo
 * @author: xue
 * @Date: 2023/8/27 15:25
 */
@Data
public class ContentWordVo extends CourseWordBank {

    /**
     * 内容表id
     */
    private Long contentBankId;

    /**
     * 节点名称
     */
    private String structName;

}
