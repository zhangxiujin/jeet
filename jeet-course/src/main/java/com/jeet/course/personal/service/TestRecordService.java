package com.jeet.course.personal.service;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jeet.course.personal.vo.*;
import com.jeet.course.study.domain.CoursePracticeRecord;

import java.util.List;
import java.util.Map;

/**
 * @PackageName : com.jeet.course.personal.testRecord.service
 * @FileName : testRecordService
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/7 16:10
 * @Version : 1.0.0
 */
public interface TestRecordService {

    List<CoursePracticeRecord> query (TestRecordVo testRecordVo);

    List<WordTestVo> queryWorkBankId(Long recordId) throws JsonProcessingException;

    List<BankErrorVo> queryProjectId(Long recordId);

    List<BankErrorVo> queryChooseId(Long recordId) throws JsonProcessingException;

    List<BankErrorVo> querySimId(Long recordId);

    List select(TestRecordVo testRecordVo) throws JsonProcessingException;

    JSONObject queryPracticeIfQualified();
}
