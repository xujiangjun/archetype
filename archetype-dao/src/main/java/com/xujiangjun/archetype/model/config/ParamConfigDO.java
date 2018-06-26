package com.xujiangjun.archetype.model.config;

import com.xujiangjun.archetype.model.base.ComplexBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 参数配置表实体类
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ParamConfigDO extends ComplexBaseDO {

    /** 参数描述 **/
    private String paramName;

    /** 参数编号 **/
    private String paramNo;

    /** 参数值 **/
    private String paramValue;

    /** 备注 **/
    private String remark;

}