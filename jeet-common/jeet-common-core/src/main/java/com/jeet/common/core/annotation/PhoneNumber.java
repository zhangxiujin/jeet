package com.jeet.common.core.annotation;

import com.jeet.common.core.validated.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author zhangxiujin
 * @date 2021/9/6
 * 身份证号验证
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PhoneNumberValidator.class})
public @interface PhoneNumber {
    // 校验出错时默认返回的消息
    String message() default "手机号错误";
    //分组校验
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
