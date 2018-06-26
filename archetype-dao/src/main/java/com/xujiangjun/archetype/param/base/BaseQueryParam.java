package com.xujiangjun.archetype.param.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 基础查询参数（分页、排序）
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Data
public class BaseQueryParam implements Serializable {

    private static final long serialVersionUID = 1718654636074211851L;

    /** 查询起始位置 **/
    private int offset;

    /** 当前页 **/
    private int page;

    /** 每页大小 **/
    private int pageSize;

    /** 自定义排序 **/
    private List<String> sorts;

    /**
     * 根据当前页和每页大小计算查询起始位置
     */
    public void setOffset() {
        if (this.page < 1) {
            this.page = 1;
        }
        this.offset = (this.page - 1) * this.pageSize;
    }
}
