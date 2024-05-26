package com.jeet.course.personal.vo;

import com.jeet.course.personal.domain.PersonQuestion;
import lombok.Data;

@Data
public class QuestionVo extends PersonQuestion {
    /**
     * 错误题
     */
    private Integer errorNumber;

    private String structName;
}
