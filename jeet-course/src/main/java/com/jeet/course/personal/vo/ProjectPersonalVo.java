package com.jeet.course.personal.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProjectPersonalVo extends ErrorBankVo{
    /**
     * 项目题名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String details;

    /**
     * 附件信息
     */
    private List<AttachUrl> attachUrls;

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
    }

}
