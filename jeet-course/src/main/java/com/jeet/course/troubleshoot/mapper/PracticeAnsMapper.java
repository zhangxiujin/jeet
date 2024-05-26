package com.jeet.course.troubleshoot.mapper;

import com.jeet.course.personal.domain.PersonQuestion;
import com.jeet.course.study.domain.CourseStudyReply;
import com.jeet.course.troubleshoot.vo.QuestionsVo;
import com.jeet.course.troubleshoot.vo.ReplyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeAnsMapper {


    /**
     * 保存回答数据
     */
    void insertAnswer(CourseStudyReply courseReply);

    /**
     * 修改提问状态
     */
    void updateState(Long id);

    /**
     * 查询是否回答的问题
     */
    List<QuestionsVo> selectAnsweredList(
            @Param("status") String status,
            @Param("studentIds") List<Long> studentIds,
            @Param("structId") Long structId,
            @Param("userId") Long userId,
            @Param("courseIds") List<Long> courseIds
    );

    /**
     * 查询回答的问题
     */
    ReplyVo selectAnswer(Long questionId);



}
