package com.jeet.common.core.validated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
 
    private String[] strValues;
    private int[] intValues;
 
    @Override
    public void initialize(EnumValue constraintAnnotation) {
        strValues = constraintAnnotation.stringValue();
        intValues = constraintAnnotation.intValue();
    }
 
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;
 
        if (!(value instanceof String) && !(value instanceof Integer)) return true;
 
        if (strValues != null && strValues.length > 0 && value instanceof String) {
            return Arrays.asList(strValues).contains(value);
        }
 
        if (intValues != null && intValues.length > 0 && value instanceof Integer) {
            return Arrays.stream(intValues).anyMatch(v -> v == (int) value);
        }
 
        return false;
    }
}