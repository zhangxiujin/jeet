package com.jeet.course.bank.vo;

import com.jeet.course.bank.domain.CourseBankAttachment;
import com.jeet.course.bank.domain.CourseProjectBank;
import com.jeet.course.check.vo.ProjectAnnexVo;
import lombok.Data;

import java.util.List;

@Data
public class ProjectBankVo extends CourseProjectBank {
    /**
     * 结构名称
     */
    private String structName;

    /**
     * 回答
     */
    private List<ProjectAnnexVo> projectInput;

    /**
     * 成绩
     */
    private Integer score;

    /**
     * 评语
     */
    private String comment;
    /**
     * 附件
     */
    private List<Attach> attachList;
    /**
     * 是否合格
     */
    private String correct;

    @Data
    static class Attach {
        /**
         * 附件名称
         */
        private String name;
        /**
         * 附件扩展名
         */
        private String extName;
        /**
         * 附件地址
         */
        private String url;
    }
}

