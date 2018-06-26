package com.xujiangjun.archetype.model.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 数据库表6个基础字段
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ComplexBaseDO extends BaseDO {

    /** 是否删除：0-未删除; 1-已删除(参考YnEnum) **/
    private Integer isDeleted;

    /** 创建人 **/
    private String creator;

    /** 修改人 **/
    private String modifier;
}
