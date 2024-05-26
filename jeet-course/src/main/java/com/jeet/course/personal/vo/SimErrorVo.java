package com.jeet.course.personal.vo;

import lombok.Data;

/**
 * @PackageName : com.jeet.course.personal.vo
 * @FileName : SimVo
 * @Description :
 * @Author 李宇乐
 * @Date 2023/9/12 14:34
 * @Version : 1.0.0
 */
@Data
public class SimErrorVo extends BankErrorVo{

    private String SimpleName;

    private String InputAnswer;
}
