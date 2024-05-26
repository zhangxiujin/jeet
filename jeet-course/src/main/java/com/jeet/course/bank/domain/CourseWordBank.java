package com.jeet.course.bank.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Desc: 课程单词库类
 * @author: 李宇乐
 * @Date: 2023/8/11 10:03
 */
@Data
public class CourseWordBank extends BaseEntity {

    /**
     * 单词库id
     */
    private Long id;
    /**
     * 中文名称
     */
    private String zhName;
    /**
     * 英文名称
     */
    private String enName;
    /**
     *排序
     */
    private Integer sort;
    /**
     * 删除标识
     */
    private String delFlag;
    /**
     * 父id
     */
    private Long structId;
    /**
     * 0代表启用，1代表停用
     */
    private String status;


}
