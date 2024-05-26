package com.jeet.course.personal.mapper;

import com.jeet.course.personal.domain.PersonQuestion;
import com.jeet.course.personal.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalQuestionMapper {
    /**
     * 查询练习数据
     * @param questionVo
     * @return
     */
    List<QuestionVo> selectQuestion(QuestionVo questionVo);

    /**
     * 查询单词题库
     */
    PersonQuestion selectAnswers(PersonQuestion personQuestion);

    /**
     * 查询选择题库
     */
    List<ChooseErrorBankVo> selectChoose(@Param("bankIdList") List<Long> bandIdList);

    /**
     * 查询项目题库
     */
    List<ProjectPersonalVo> selectProject(@Param("bankIdList") List<Long> bandIdList);

    /**
     * 查询项目题库
     */
    List<SimpleErrorBankVo> selectSimple(@Param("bankIdList") List<Long> bandIdList);
}
