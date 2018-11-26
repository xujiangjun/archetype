package com.xujiangjun.archetype.exception;

import com.xujiangjun.archetype.enums.ResponseEnum;
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

    public BusinessException(ResponseEnum responseEnum){
        super(responseEnum.getMessage());
        this.code = responseEnum.getCode();
    }

    public BusinessException(ResponseEnum responseEnum, String message){
        super(message);
        this.code = responseEnum.getCode();
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
