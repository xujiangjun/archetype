package com.xujiangjun.archetype.dao.base;

/**
 * 基础Mapper
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public interface BaseMapper<T> {

    int insert(T data);

    int insertSelective(T data);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T data);

    int updateByPrimaryKey(T data);
}
