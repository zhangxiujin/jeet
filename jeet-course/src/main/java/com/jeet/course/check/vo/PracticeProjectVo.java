package com.jeet.course.check.vo;

import lombok.Data;

import java.util.List;

/**
 * @Desc: PracticeProjectVo
 * @author: xue
 * @Date: 2023/9/12 17:53
 */
@Data
public class PracticeProjectVo {

    /**
     * id
     */
    private Long id;

    /**
     * 成绩
     */
    private Integer score;

    /**
     *
     * 是否合格
     */
    private List<ScoreVo> correct;

    /**
     * 评语
     */
    private String comment;

}
