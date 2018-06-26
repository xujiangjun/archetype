package com.xujiangjun.archetype.model.base;

import lombok.Data;

import java.util.Date;

/**
 * 数据库表基础字段(isDeleted、creator和modifier视情况创建)
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Data
public class BaseDO {
    /** 自增主键 */
    private Integer id;

    /** 创建时间 */
    private Date gmtCreate;

    /** 修改时间 */
    private Date gmtModified;
}
