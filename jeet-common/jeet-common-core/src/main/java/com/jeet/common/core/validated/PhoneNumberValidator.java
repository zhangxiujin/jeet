package com.jeet.common.core.validated;

import cn.hutool.core.util.PhoneUtil;
import com.jeet.common.core.annotation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhangxiujin
 * @date 2021/9/6
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber,String> {

    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return PhoneUtil.isPhone(value);
    }
}
