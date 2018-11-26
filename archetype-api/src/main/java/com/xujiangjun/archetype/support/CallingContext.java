package com.xujiangjun.archetype.support;

import lombok.Data;

import java.io.Serializable;

/**
 * Api涉及数据增删改操作的接口传参须包含调用上下文
 *
 * @author xujiangjun
 * @since 2018.06.28
 */
@Data
public class CallingContext implements Serializable {

    private static final long serialVersionUID = 4526074991575905737L;

    /** 系统代码Reference:SystemEnum.java */
    private String systemCode;

    /** 操作人 */
    private String operatorName;
}
