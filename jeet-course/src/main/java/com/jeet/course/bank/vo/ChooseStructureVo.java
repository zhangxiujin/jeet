package com.jeet.course.bank.vo;

import com.jeet.course.bank.domain.CourseChooseBank;
import com.jeet.course.check.vo.ChooseAnswerVo;
import lombok.Data;

import java.util.List;


@Data
public class ChooseStructureVo extends CourseChooseBank {

    /**
     * 结构名称
     */
    private String structName;

    /**
     * 成绩
     */
    private Integer score;

    /**
     * 输入的答案
     */
    private List<String> inputAnswer;

    /**
     * 对或错
     */
    private String correct;

    /**
     * 评语
     */
    private String comment;


}
