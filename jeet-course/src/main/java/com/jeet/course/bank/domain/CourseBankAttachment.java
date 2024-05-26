package com.jeet.course.bank.domain;

import com.jeet.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Desc: 题库附件实体类
 * @author: qingmei
 * @Date 2023/8/21 16:20
 */
@Data
public class CourseBankAttachment extends BaseEntity {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 所属结构id
     */
    private Long bankId;
    /**
     * 扩展名
     */
    private String extName;
    /**
     * 文件上传路径
     */
    private String url;
    /**
     * 删除标识
     */
    private String delFlag;
}
