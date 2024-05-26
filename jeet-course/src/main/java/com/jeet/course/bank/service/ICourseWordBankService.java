package com.jeet.course.bank.service;

import com.jeet.course.bank.domain.CourseWordBank;
import com.jeet.course.bank.vo.BankStructureVo;
import com.jeet.course.bank.vo.QueryListVo;

import java.util.List;

public interface ICourseWordBankService {

    void saveWord(CourseWordBank courseWordBank);

    /**
     * 查询单词列表(包含父节点以及子节点下的单词)
     */
    List<BankStructureVo> queryWordList(QueryListVo queryListVo);

    /**
     * 通过id查询单词列表
     */
    CourseWordBank queryWord(Long id);

    /**
     * 通过id更新单词
     */
    void modifyWord(CourseWordBank courseWordBank);

    /**
     * 通过id删除单词
     */
    void deleteWord(Long id);

    /**
     * 通过ids删除单词
     */
    void deleteWords(List<Long> ids);

    /**
     * 查询未选择的题
     */
    List<BankStructureVo> queryWordByContentId(QueryListVo queryListVo);

}
