package com.jeet.course.check.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc: PracticeVo
 * @author: xue
 * @Date: 2023/9/7 15:22
 */
@Data
public class PracticeVo {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 组织
     */
    private String deptName;

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
     * 节点id
     */
    private Long structId;

    /**
     * 内容id
     */
    private Long contentId;

    /**
     * 题库类型
     */
    private String type;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 练习记录id
     */
    private Long id;

}
