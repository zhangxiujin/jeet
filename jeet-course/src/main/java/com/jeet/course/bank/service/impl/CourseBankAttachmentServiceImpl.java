package com.jeet.course.bank.service.impl;

import com.jeet.common.core.utils.IdUtil;
import com.jeet.common.security.utils.SecurityUtils;
import com.jeet.course.bank.domain.CourseBankAttachment;
import com.jeet.course.bank.mapper.CourseBankAttachmentMapper;
import com.jeet.course.bank.service.ICourseBankAttachmentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Desc: CourseBankAttachmentServiceImpl
 * @author: qingmei
 * @Date 2023/8/21 16:31
 */
@Service
public class CourseBankAttachmentServiceImpl implements ICourseBankAttachmentService {
    @Autowired
    private CourseBankAttachmentMapper courseBankAttachmentMapper;

    @Override
    public void insertCourseBankAttachment(CourseBankAttachment courseBankAttachment) {
        courseBankAttachment.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        courseBankAttachment.setCreateTime(new Date(System.currentTimeMillis()));
        courseBankAttachment.setDelFlag("0");
        courseBankAttachmentMapper.insertCourseBankAttachment(courseBankAttachment);
    }

    @Override
    public List<CourseBankAttachment> selectAttachments(Long bankId) {
        return courseBankAttachmentMapper.selectAttachments(bankId);
    }

    @Override
    public void removeAttachments(List<Long> ids) {
        courseBankAttachmentMapper.deleteAttachments(ids);
    }

    @Override
    public void removeAttachments(String... urls) {
        courseBankAttachmentMapper.deleteAttachmentsByUrls(urls);
    }


}
