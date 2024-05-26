package com.jeet.course.bank.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ChooseVo {

    /**
     * 题目
     */
    private String title;
    /**
     * 状态
     */
    private String status;
    /**
     * 搜索条件
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params = new HashMap<>();
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 结构id以及子节点结构id
     */
    private List<Long> structIdList;

    /**
     * 内容id
     */
    private Long contentId;

}
