package com.jeet.course.check.vo;

import lombok.Data;

/**
 * @Desc: ScoreVo
 * @author: xue
 * @Date: 2023/9/12 18:02
 */
@Data
public class ScoreVo {

    /**
     * 题库id
     */
    private Long bankId;

    /**
     * 是否合格
     */
    private String correct;

}
