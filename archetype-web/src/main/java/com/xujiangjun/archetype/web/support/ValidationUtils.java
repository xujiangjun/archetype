package com.xujiangjun.archetype.web.support;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 将bindingResult转换为错误字符串
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public class ValidationUtils {

    public static final String SEPARATOR = ",";

    /**
     * 将字段错误组装成字符串，使用逗号分隔
     *
     * @param bindingResult 绑定结果
     * @return 错误信息字符串
     */
    public static String getAllErrorMsg(BindingResult bindingResult){
        StringBuilder sb = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getDefaultMessage()).append(SEPARATOR);
        }
        String errorMsg = sb.substring(0, sb.lastIndexOf(SEPARATOR));
        return errorMsg;
    }
}
