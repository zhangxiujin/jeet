package com.jeet.course.troubleshoot.service;

import com.jeet.course.personal.domain.PersonQuestion;
import com.jeet.course.study.domain.CourseStudyReply;
import com.jeet.course.troubleshoot.vo.QuestionsVo;
import com.jeet.course.troubleshoot.vo.ReplyVo;
import com.jeet.course.troubleshoot.vo.SaveAnswerVo;

import java.util.List;


public interface IPracticeAnsService {



    /**
     * 保存回答问题情况
     * @param courseReply
     */
    void saveAnswer(SaveAnswerVo courseReply);

    /**
     * 查询提问数据列表
     */
    List<QuestionsVo> queryReteList(
            String status, Long userId, Long structId,
            List<Long> studentIdList, List<Long> courseIds);

    /**
     * 查询问题的回答
     */
    ReplyVo queryAnswer(Long questionId);

}
