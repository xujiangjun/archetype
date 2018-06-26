package com.xujiangjun.archetype.exception;

import com.xujiangjun.archetype.enums.ErrorEnum;
import lombok.Getter;

/**
 * 自定义业务异常类
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public class BusinessException extends RuntimeException {

    @Getter
    private int code;

    public BusinessException(ErrorEnum errorEnum){
        super(errorEnum.getMessage());
        this.code = errorEnum.getCode();
    }

    public BusinessException(ErrorEnum errorEnum, String message){
        super(message);
        this.code = errorEnum.getCode();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
