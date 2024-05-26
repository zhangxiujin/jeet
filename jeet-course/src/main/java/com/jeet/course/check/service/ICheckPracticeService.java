package com.jeet.course.check.service;

import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.course.bank.vo.ChooseStructureVo;
import com.jeet.course.check.vo.*;
import com.jeet.course.study.domain.CoursePracticeRecord;
import com.jeet.course.study.vo.WordContentVo;

import java.util.List;

public interface ICheckPracticeService {

    /**
     * 查询参与考试的用户列表
     */
    List<PracticeUserVo> queryPracticeUser(PracticeVo practiceVo);

    /**
     * 查询题库类型
     */
    List<PracticeRecordVo> queryPractice(PracticeVo practiceVo);

    /**
     * 查询未审核的练习记录
     * @param queryPracticeRecordsVo
     * @return 练习记录
     */
    List<PracticeRecordVo> queryUnCheckPractice(QueryPracticeRecordsVo queryPracticeRecordsVo);

    /**
     * 查询已审核的练习记录
     * @param queryPracticeRecordsVo
     * @return
     */
    List<PracticeRecordVo> queryCheckedPractice(QueryPracticeRecordsVo queryPracticeRecordsVo);

    /**
     * 查询详情
     */
    Object queryPracticeDetails(PracticeVo practiceVo);

    /**
     * 审核成绩
     */
    void modifyWord(WordContentVo wordContentVo);

    /**
     * 简答成绩审核
     */
    void modifySimple(PracticeProjectVo practiceProjectVo);

    /**
     * 项目成绩审核
     */
    void modifyProject(PracticeProjectVo practiceProjectVo);

    /**
     * 选择成绩审核
     */
    void modifyChoose(ChoosePracticeVo choosePracticeVo);

}
