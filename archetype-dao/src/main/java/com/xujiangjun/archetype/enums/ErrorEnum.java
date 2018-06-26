package com.xujiangjun.archetype.enums;

import lombok.Getter;

/**
 * 结果枚举类（推荐使用）
 *
 * 错误码的定义是为了便于在多系统情况下出现问题时能够快速定位到某个系统的某个模块，在定位问题时更加方便地追根溯源。
 *
 * 注：公共错误码和项目错误码应拆分为两个枚举类，方便起见此处不进行拆分。
 *
 * 目前公共错误码总共分为4位，第1位为错误类别码，第2-4为具体异常定义码。
 *
 * 目前项目错误码总共分为7位，第1位为平台码或团队码，第2-3位系统码，第4位为错误类别码，第5-7位为具体异常定义码。
 * 第1位(根据具体情况可舍弃)：1-电商团队; 2-云配团队; 3-云修团队
 * 第2-3位：00-代表平台级别公共错误码; 01-代表engine项目; 02-代表legend项目;
 * 第4位：0-表示数据库操作类错误；1-表示参数校验类错误; 2-表示异常数据类错误; 3-异常流程或禁止操作类错误; 4-查询无数据类错误;
 * 第5-7位：具体异常定义码。
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public enum ErrorEnum {
    SYSTEM_ERROR(1000, "系统出错啦"),
    CHECK_FAIL(1001, "校验不通过"),
    NOT_EXISTS(1002, "数据不存在"),
    FORMAT_ERROR(1003, "格式化错误"),
    PARAM_IS_NULL(1004, "参数为空"),
    REDIS_ERROR(1005, "redis操作异常"),
    MAIL_SEND_FAIL(1006, "邮件发送失败");

    @Getter
    private int code;

    @Getter
    private String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
