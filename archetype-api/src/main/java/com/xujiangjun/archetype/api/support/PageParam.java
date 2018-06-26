package com.xujiangjun.archetype.api.support;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询参数
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Data
public class PageParam implements Serializable {

    private static final long serialVersionUID = -7555727160174631662L;

    /** 默认1代表第一页 */
    private static final int FIRST_PAGE = 1;

    /** 默认分页大小 **/
    private static final int DEFAULT_PAGE_SIZE = 20;

    /** 最大分页大小 **/
    private static final int MAX_PAGE_SIZE = 2000;

    /** 当前页数 **/
    private Integer page;

    /** 每页大小 **/
    private Integer pageSize;

    /** 自定义排序 **/
    private List<String> sorts;

    public int getOffset(){
        if (this.page == null || this.page <= 0){
            this.page = FIRST_PAGE;
        }
        if(this.pageSize == null || this.pageSize <= 0 || this.pageSize > MAX_PAGE_SIZE){
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        return (this.page - 1)*this.pageSize;
    }

}
