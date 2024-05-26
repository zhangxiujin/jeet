package com.jeet.course.check.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc: BankAnswersVo
 * @author: xue
 * @Date: 2023/9/8 14:25
 */
@Data
public class BankAnswersVo {

    /**
     * 题库id
     */
    private Long bankId;

    /**
     * 回答
     */
    private List<ProjectAnnexVo> attachUrls;

    /**
     * 是否合格
     */
    private String correct;

}
