package com.jeet.course.personal.mapper;

import com.jeet.course.personal.vo.*;
import com.jeet.course.study.domain.CoursePracticeRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @PackageName : com.jeet.course.personal.testRecord.mapper
 * @FileName : testRecordMapper
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/7 16:11
 * @Version : 1.0.0
 */
@Repository
public interface TestRecordMapper {

    List<CoursePracticeRecord> selectTest(TestRecordVo testRecordVo);

    String selectTestAnswer(Long recordId);

    CoursePracticeRecord selectWordId(List<Long> recordId);

    List<ErrorChooseBankVo> selectChooseId(@Param("bankIdList")List<Long> recordId);

    List<ProjectErrorBankVo> selectProjectId(@Param("bankIdList") List<Long> recordId);

    List<SimErrorVo> selectSimId(@Param("bankIdList")List<Long> recordId);

    /**
     * 查询各个内容的各类型题的最新练习记录分数
     * @param userId 当前登录学员
     * @return
     */
    List<PracticeScoresVo> selectPracticeScores(@Param("userId") Long userId);
}
