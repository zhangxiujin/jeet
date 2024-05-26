package com.jeet.course.bank.vo;

import com.jeet.course.bank.domain.CourseSimpleBank;
import lombok.Data;

/**
 * @Desc: SimpleVo
 * @author: xue
 * @Date: 2023/9/3 20:59
 */
@Data
public class SimpleVo extends CourseSimpleBank {

    /**
     * 节点名称
     */
    private String structName;

    /**
     * 输入的答案
     */
    private String answerInput;

    /**
     * 成绩
     */
    private Integer score;

    /**
     * 评语
     */
    private String comment;

    /**
     * 是否合格
     */
    private String correct;

}
