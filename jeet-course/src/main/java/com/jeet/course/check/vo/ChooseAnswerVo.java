package com.jeet.course.check.vo;

import lombok.Data;

import java.util.List;

/**
 * @Desc: ChooseAnswerVo
 * @author: xue
 * @Date: 2023/9/11 8:55
 */
@Data
public class ChooseAnswerVo {

    /**
     * 课程id
     */
    private Long bankId;

    /**
     * 选择答案
     */
    private List<String> options;

    /**
     * 对或错
     */
    private String correct;

}
