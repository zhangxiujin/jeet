package com.jeet.course.personal.controller;

import com.jeet.common.core.web.domain.AjaxResult;
import com.jeet.common.datasource.config.web.controller.BaseController;
import com.jeet.common.datasource.config.web.page.TableDataInfo;
import com.jeet.course.personal.domain.PersonQuestion;
import com.jeet.course.personal.service.IPersonalQuestionService;
import com.jeet.course.personal.vo.ErrorBankVo;
import com.jeet.course.personal.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/question")
public class PersonalQuestionController extends BaseController {

    @Autowired
    IPersonalQuestionService personalQuestionService;

    @GetMapping("/queryQuestion")
    public TableDataInfo queryQuestion(QuestionVo questionVo) {
        startPage();
        List<QuestionVo> questionVos = personalQuestionService.queryQuestion(questionVo);
        return getDataTable(questionVos);
    }

    @GetMapping("/queryErrorBank")
    public AjaxResult queryErrorBank(PersonQuestion personQuestion) {
        List<ErrorBankVo> errorBankVoList = personalQuestionService.queryErrorBank(personQuestion);
        return AjaxResult.success(errorBankVoList);
    }
}