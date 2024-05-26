package com.jeet.course.bank.service.impl;

import com.jeet.common.core.context.SecurityContextHolder;
import com.jeet.common.core.exception.ServiceException;
import com.jeet.common.core.utils.IdUtil;
import com.jeet.course.bank.domain.CourseWordBank;
import com.jeet.course.bank.mapper.CourseWordBankMapper;
import com.jeet.course.bank.service.ICourseWordBankService;
import com.jeet.course.bank.vo.BankStructureVo;
import com.jeet.course.bank.vo.QueryListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Desc: CourseWordBankServiceImpl
 * @author: 李宇乐
 * @Date: 2023/8/14 10:04
 */
@Service
public class CourseWordBankServiceImpl implements ICourseWordBankService {

    @Autowired
    private CourseWordBankMapper courseWordBankMapper;

    @Override
    public void saveWord(CourseWordBank courseWordBank) {
        courseWordBank.setId(IdUtil.nextId());
        Long userId = SecurityContextHolder.getUserId();
        courseWordBank.setCreateBy(String.valueOf(userId));
        courseWordBank.setCreateTime(new Date(System.currentTimeMillis()));
        courseWordBank.setDelFlag("0");
        Integer count = courseWordBankMapper.selectCount(courseWordBank);
        if(count > 0) {
            throw new ServiceException("单词已存在");
        }
        courseWordBankMapper.insertWordBank(courseWordBank);
    }

    @Override
    public List<BankStructureVo> queryWordList(QueryListVo queryListVo) {
        return courseWordBankMapper.selectWordList(queryListVo);
    }

    @Override
    public CourseWordBank queryWord(Long id) {
        return courseWordBankMapper.selectWord(id);
    }

    @Override
    public void modifyWord(CourseWordBank courseWordBank) {
        courseWordBankMapper.updateWord(courseWordBank);
    }

    @Override
    public void deleteWord(Long id) {
        courseWordBankMapper.deleteWord(id);
    }

    @Override
    public void deleteWords(List<Long> ids) {
        courseWordBankMapper.deleteWords(ids);
    }

    @Override
    public List<BankStructureVo> queryWordByContentId(QueryListVo queryListVo) {
        return courseWordBankMapper.selectWordByContentId(queryListVo);
    }

}
