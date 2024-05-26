package com.jeet.course.bank.service;

import com.jeet.course.bank.domain.CourseBankAttachment;

import java.util.List;

/**
 * 课程题库附件服务接口
 */
public interface ICourseBankAttachmentService {

    /**
     * 保存附件
     * @param courseBankAttachment
     */
    void insertCourseBankAttachment(CourseBankAttachment courseBankAttachment);

    /**
     * 查询某个题的附件
     */
    List<CourseBankAttachment> selectAttachments(Long bankId);

    /**
     * 删除附件
     * @param ids
     */
    void removeAttachments(List<Long> ids);

    /**
     * 通过附件url删除
     */
    void removeAttachments(String... urls);

}
