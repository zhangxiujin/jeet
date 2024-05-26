package com.jeet.course.bank.service.impl;

import com.jeet.common.core.context.SecurityContextHolder;
import com.jeet.common.core.utils.IdUtil;
import com.jeet.course.bank.domain.CourseChooseBank;
import com.jeet.course.bank.mapper.CourseChooseBankMapper;
import com.jeet.course.bank.service.ICourseChooseBankService;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.bank.vo.ChooseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * @Desc: CourseChooseBankServiceImpl
 * @author: 李宇乐
 * @Date: 2023/8/16 10:14
 */
@Service
public class CourseChooseBankServiceImpl implements ICourseChooseBankService {

    @Autowired
    private CourseChooseBankMapper courseChooseBankMapper;

    @Override
    public void saveCourseChooseBank(CourseChooseBank courseChooseBank) {
        courseChooseBank.setId(IdUtil.nextId());
        Long userId = SecurityContextHolder.getUserId();
        courseChooseBank.setCreateBy(String.valueOf(userId));
        courseChooseBank.setCreateTime(new Date(System.currentTimeMillis()));
        courseChooseBank.setDelFlag("0");
        courseChooseBankMapper.insertCourseChooseBank(courseChooseBank);
    }

    @Override
    public List<ChooseStructureVo> queryChoose(ChooseVo chooseVo) {
        List<ChooseStructureVo> choose = courseChooseBankMapper.selectCourseChooseBank(chooseVo);
        return choose;
    }

    @Override
    public void removeChoose(Long id) {
        courseChooseBankMapper.deleteChoose(id);
    }

    @Override
    public void removeChooseList(List<Long> ids) {
        courseChooseBankMapper.deleteChooseList(ids);
    }

    @Override
    public ChooseStructureVo queryChooseId(Long id) {
        ChooseStructureVo courseChooseBank = courseChooseBankMapper.selectChoose(id);
        return courseChooseBank;
    }

    @Override
    public void modifyChoose(CourseChooseBank courseChooseBank) {
        courseChooseBankMapper.updateChoose(courseChooseBank);
    }

    @Override
    public List<ChooseStructureVo> queryChooseByContentId(ChooseVo chooseVo) {
        return courseChooseBankMapper.selectChooseByContentId(chooseVo);
    }
}
