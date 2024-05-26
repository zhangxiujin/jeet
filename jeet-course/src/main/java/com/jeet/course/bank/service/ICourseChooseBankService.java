package com.jeet.course.bank.service;

import com.jeet.course.bank.domain.CourseChooseBank;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.bank.vo.ChooseVo;

import java.util.List;

public interface ICourseChooseBankService {

    /**
     * 新增选择题库数据
     * @param courseChooseBank
     */
    void saveCourseChooseBank(CourseChooseBank courseChooseBank);

    /**
     * 查询选择题库数据
     */
    List<ChooseStructureVo> queryChoose(ChooseVo chooseVo);

    /**
     * 删除选择题库数据
     */
    void removeChoose(Long id);

    /**
     * 多条删除
     */
    void removeChooseList(List<Long> ids);

    /**
     * 通过id查询
     */
    ChooseStructureVo queryChooseId(Long id);

    /**
     * 更新选择题库数据
     */
    void modifyChoose(CourseChooseBank courseChooseBank);

    /**
     * 查询未选择的题
     */
    List<ChooseStructureVo> queryChooseByContentId(ChooseVo chooseVo);

}
