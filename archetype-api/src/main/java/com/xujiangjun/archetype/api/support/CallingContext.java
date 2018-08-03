package com.xujiangjun.archetype.api.support;

import lombok.Data;

import java.io.Serializable;

/**
 * Api涉及数据更新的接口传参必须包含调用上下文
 *
 * @author xujiangjun
 * @since 2018.06.28
 */
@Data
public class CallingContext implements Serializable {

    private static final long serialVersionUID = 4526074991575905737L;

    /** 系统名称 */
    private String systemName;

    /** 操作人 */
    private String operatorName;
}
