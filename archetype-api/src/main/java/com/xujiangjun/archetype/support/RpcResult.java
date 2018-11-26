package com.xujiangjun.archetype.support;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装结果类
 *
 * 思考：
 *   1.取消success字段，使用(code == 0)来表示成功。
 *   2.(code > 0)表示可以直接弹出的错误提示。
 *   3.(code < 0)表示需要前端根据错误码进行转换的错误提示。
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
        result.code = 0;
        result.success = true;
        result.data = data;
        return result;
    }

    public static <T> RpcResult<T> wrapSuccessfulResult(T data, int total){
        RpcResult<T> result = new RpcResult<>();
        result.code = 0;
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
