package com.xujiangjun.archetype.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统的枚举类
 *
 * @author xujiangjun
 * @since 2018.11.26
 */
public enum SystemEnum {
    /** 系统id(各系统错误码首部以系统id开始), 系统名, 系统中文描述 */
    ARCHETYPE(1, "archetype", "骨架工程");

    @Getter
    private int code;

    @Getter
    private String name;

    @Getter
    private String description;

    public static final Map<String, SystemEnum> SYSTEM_ENUM_MAP = new HashMap<>();

    SystemEnum(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    static {
        for (SystemEnum systemEnum : values()) {
            SYSTEM_ENUM_MAP.put(systemEnum.getName(), systemEnum);
        }
    }

    public static SystemEnum getByCode(String name){
        return SYSTEM_ENUM_MAP.get(name);
    }

}
