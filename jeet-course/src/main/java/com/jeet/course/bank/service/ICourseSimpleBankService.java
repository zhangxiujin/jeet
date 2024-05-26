package com.jeet.course.bank.service;

import com.jeet.course.bank.domain.CourseSimpleBank;
import com.jeet.course.bank.vo.SimpleBankVo;
import com.jeet.course.bank.vo.SimpleVo;

import java.util.List;

public interface ICourseSimpleBankService {

    /**
     * 新增简答题
     * @param courseSimpleBank
     */
    void save(CourseSimpleBank courseSimpleBank);

    /**
     * 查询简答题
     */
    List<SimpleVo> query(SimpleBankVo simpleBankVo);

    /**
     * 通过id查询
     */
    SimpleVo queryById(Long id);

    /**
     * 修改简答题
     */
    void modify(CourseSimpleBank courseSimpleBank);

    /**
     * 删除单条简答题
     */
    void removeById(Long id);

    /**
     * 批量删除
     */
    void removeList(List<Long> ids);

    /**
     * 通过未被内容绑定的数据
     */
    List<SimpleVo> queryByContentId(SimpleBankVo simpleBankVo);


}
