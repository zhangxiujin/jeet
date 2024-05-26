package com.jeet.course.bank.mapper;

import com.jeet.course.bank.domain.CourseBankAttachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseBankAttachmentMapper {
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
     * @param list
     */
    void deleteAttachments(List<Long> list);

    /**
     * 通过多个url删除附件
     * @param urls
     */
    void deleteAttachmentsByUrls(@Param("urls") String... urls);
}
