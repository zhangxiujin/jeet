package com.jeet.course.check.vo;

import com.jeet.course.bank.vo.ChooseStructureVo;
import lombok.Data;

import java.util.List;

/**
 * @Desc: ChoosePracticeVo
 * @author: xue
 * @Date: 2023/9/11 11:50
 */
@Data
public class ChoosePracticeVo {

    /**
     * 答案
     */
    private List<ChooseStructureVo> answer;

    /**
     * id
     */
    private Long id;

    /**
     * 评语
     */
    private String comment;

}
