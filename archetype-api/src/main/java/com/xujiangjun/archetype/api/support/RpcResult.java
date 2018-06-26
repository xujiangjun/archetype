package com.xujiangjun.archetype.api.support;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装结果类
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Data
public class RpcResult<T> implements Serializable {

    private static final long serialVersionUID = 5364158200817095814L;

    /** 提示码 **/
    private int code;

    /** 提示信息 **/
    private String message;

    /** 是否成功 **/
    private boolean success;

    /** 返回数据 **/
    private T data;

    /** 总条数 **/
    private int total;

    public static <T> RpcResult<T> wrapSuccessfulResult(T data){
        RpcResult<T> result = new RpcResult<>();
        result.success = true;
        result.data = data;
        return result;
    }

    public static <T> RpcResult<T> wrapSuccessfulResult(T data, int total){
        RpcResult<T> result = new RpcResult<>();
        result.success = true;
        result.data = data;
        result.total = total;
        return result;
    }

    public static <T> RpcResult<T> wrapFailureResult(int code, String message){
        RpcResult<T> result = new RpcResult<>();
        result.code = code;
        result.message = message;
        result.success = false;
        return result;
    }

    public static <T> RpcResult<T> wrapFailureResult(RpcResult<?> otherResult) {
        RpcResult<T> result = new RpcResult<>();
        result.code = otherResult.getCode();
        result.message = otherResult.getMessage();
        result.success = false;
        return result;
    }
}
