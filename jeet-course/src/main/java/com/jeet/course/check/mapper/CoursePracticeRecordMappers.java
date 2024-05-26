package com.jeet.course.check.mapper;

import com.jeet.course.check.vo.*;
import com.jeet.course.study.domain.CoursePracticeRecord;
import com.jeet.course.study.vo.WordContentVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursePracticeRecordMappers {

    /**
     * 根据条件查询数据
     */
    List<PracticeUserVo> selectPracticeUser(PracticeVo practiceVo);

    /**
     * 查询题库类型
     */
    List<PracticeRecordVo> selectPractice(PracticeVo practiceVo);

    /**
     * 通过id查询数据
     */
    CoursePracticeRecord selectById(Long id);

    /**
     * 审核单词
     */
    void updatePractice(CoursePracticeRecord coursePracticeRecord);

    /**
     * 查询未审核|已审核的练习记录
     * @param queryPracticeRecordsVo
     * @return 练习记录
     */
    List<PracticeRecordVo> selectPracticeRecords(
            QueryPracticeRecordsVo queryPracticeRecordsVo);

}
