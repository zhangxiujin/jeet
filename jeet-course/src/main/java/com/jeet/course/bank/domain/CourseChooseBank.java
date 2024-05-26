package com.jeet.course.bank.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Desc: CourseChooseBank
 * @author: 李宇乐
 * @Date: 2023/8/16 8:54
 */
@Data
public class CourseChooseBank extends BaseEntity {

    /**
     * id
     */
    private Long id;
    /**
     * 题目
     */
    private String title;
    /**
     * 选项
     */
    private String options;
    /**
     * 答案
     */
    private String answer;
    /**
     * 类型 单选0 多选1
     */
    private String type;
    /**
     * 删除标识
     */
    private String delFlag;
    /**
     * 状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 结构id
     */
    private Long structId;


}
