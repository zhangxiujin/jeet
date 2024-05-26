package com.jeet.course.personal.vo;

import lombok.Data;

import java.util.List;

/**
 * @PackageName : com.jeet.course.personal.vo
 * @FileName : ProjectErrorBankVo
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/12 17:25
 * @Version : 1.0.0
 */
@Data
public class ProjectErrorBankVo extends BankErrorVo {

    /**
     * 项目题名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String details;

    /**
     * 提交项目
     */
    private List<AnswerAttachUrl> attachUrls;

    /**
     * 附件信息
     */
    private List<AttachUrl> quesAttachList;

    @Data
    static class AttachUrl {
        /**
         * 附件地址
         */
        private String url;
        /**
         * 附件名称
         */
        private String name;
        /**
         * 扩展名
         */
        private String extName;
    }

    @Data
    static class AnswerAttachUrl {
        /**
         * 附件地址
         */
        private String url;
        /**
         * 附件名称
         */
        private String name;
    }

}
