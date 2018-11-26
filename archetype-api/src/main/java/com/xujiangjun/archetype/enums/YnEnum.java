package com.xujiangjun.archetype.enums;

import lombok.Getter;

/**
 * 是/否通用枚举
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public enum YnEnum {
    /** 0 对应 否 */
    NO(0),
    /** 1 对应 是 */
    YES(1);

    @Getter
    private int code;

    YnEnum(int code) {
        this.code = code;
    }
}
