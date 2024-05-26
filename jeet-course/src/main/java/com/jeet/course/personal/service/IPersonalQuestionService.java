package com.jeet.course.personal.service;

import com.jeet.course.personal.domain.PersonQuestion;
import com.jeet.course.personal.vo.ErrorBankVo;
import com.jeet.course.personal.vo.QuestionVo;

import java.util.List;

public interface IPersonalQuestionService {

    List<QuestionVo> queryQuestion(QuestionVo questionVo);

    List<ErrorBankVo> queryErrorBank(PersonQuestion personQuestion);

}