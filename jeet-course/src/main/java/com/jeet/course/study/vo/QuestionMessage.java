package com.jeet.course.study.vo;

import com.jeet.common.message.entity.Message;
import lombok.Getter;
import lombok.Setter;

/**
 * 问题消息实体
 * @author zhangxiujin
 */
@Getter
@Setter
public class QuestionMessage extends Message {
    /**
     * 问题id
     */
    private Long questionId;
    /**
     * 提问人|回复人班级
     */
    private String dept;
    /**
     * 提问人|回复人昵称
     */
    private String nickname;
    /**
     * 是否回复 1是 0否
     */
    private Integer reply = 0;
}
