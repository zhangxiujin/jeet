package com.jeet.course.bank.mapper;

import com.jeet.course.bank.domain.CourseSimpleBank;
import com.jeet.course.bank.vo.SimpleBankVo;
import com.jeet.course.bank.vo.SimpleVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseSimpleBankMapper {

    /**
     * 新增内容
     * @param courseSimpleBank
     */
    void insert(CourseSimpleBank courseSimpleBank);

    /**
     * 查询
     */
    List<SimpleVo> select(SimpleBankVo simpleBankVo);

    /**
     * id查询
     */
    SimpleVo selectById(Long id);

    /**
     * 修改数据
     */
    void update(CourseSimpleBank courseSimpleBank);

    /**
     * 删除数据
     */
    void deleteById(Long id);

    /**
     * 批量删除
     */
    void deleteList(List<Long> list);

    /**
     * 通过内容id查询
     */
    List<SimpleVo> selectByContentId(SimpleBankVo simpleBankVo);

    /**
     * 多条查询
     */
    List<SimpleVo> selectSimpleList(List<Long> list);

}
