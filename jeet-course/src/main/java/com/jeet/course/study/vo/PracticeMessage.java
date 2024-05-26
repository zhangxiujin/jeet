package com.jeet.course.study.vo;

import com.jeet.common.message.entity.Message;
import lombok.Getter;
import lombok.Setter;

/**
 * 练习消息
 * @author zhangxiujin
 */
@Getter
@Setter
public class PracticeMessage extends Message {
    /**
     * 练习id
     */
    private Long practiceId;
    /**
     * 练习人班级
     */
    private String dept;
    /**
     * 练习人昵称
     */
    private String nickname;
}
