package com.txwl.txwlplatform.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneField, Object> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneField constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return true; // 如果对象为null，则交给其他验证注解处理
        }

        if (fields == null || fields.length == 0) {
            return true; // 如果没有指定字段，则跳过验证
        }

        try {
            for (String fieldName : fields) {
                Field field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(obj);

                // 检查值是否不为null或不为空字符串
                if (value != null) {
                    if (value instanceof String) {
                        if (!((String) value).trim().isEmpty()) {
                            return true; // 找到至少一个非空字段，验证通过
                        }
                    } else {
                        return true; // 非字符串类型且不为null，验证通过
                    }
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 如果无法访问字段，记录错误或返回false
            return false;
        }

        return false; // 所有字段都为空
    }
}