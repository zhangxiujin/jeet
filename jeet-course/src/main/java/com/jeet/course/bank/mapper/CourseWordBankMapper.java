package com.jeet.course.bank.mapper;

import com.jeet.course.bank.domain.CourseWordBank;
import com.jeet.course.bank.vo.BankStructureVo;
import com.jeet.course.bank.vo.QueryListVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseWordBankMapper {

    /**
     * 新增单词
     * @param courseWordBank
     */
    void insertWordBank(CourseWordBank courseWordBank);

    /**
     * 查询单词列表
     * @param  queryListVo 查询条件
     */
    List<BankStructureVo> selectWordList(QueryListVo queryListVo);

    /**
     * 通过条件查询数据
     * @param id
     * @return
     */
    CourseWordBank selectWord(Long id);

    /**
     * 更新数据
     * @param courseWordBank
     * @return
     */
    void updateWord(CourseWordBank courseWordBank);

    /**
     * 删除数据
     * @param id
     */
    void deleteWord(Long id);

    /**'
     * 多条删除
     */
    void deleteWords(List<Long> list);

    /**
     * 查询满足条件的数量
     */
    Integer selectCount(CourseWordBank courseWordBank);

    /**
     * 查询未绑定的数据
     */
    List<BankStructureVo> selectWordByContentId(QueryListVo queryListVo);

}
