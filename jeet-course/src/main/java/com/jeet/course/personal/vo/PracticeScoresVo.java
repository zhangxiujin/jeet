package com.jeet.course.personal.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 练习分数记录vo
 * @author zhangxiujin
 */
@Getter
@Setter
public class PracticeScoresVo {
    private Long structId;
    private List<BankScore> bankScoreList;

    @Getter
    @Setter
    public static class BankScore {
        public String bankType;
        private Integer score;
    }
}
