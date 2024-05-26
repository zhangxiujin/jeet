package com.jeet.course.check.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Desc: PracticeUserVo
 * @author: xue
 * @Date: 2023/9/7 11:18
 */
@Data
public class PracticeUserVo {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
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
     * 内容id
     */
    private Long contentId;

    /**
     * 最后一次练习时间
     */
    private Date createTime;



}
