package com.xujiangjun.archetype.service.base;

import com.xujiangjun.archetype.support.Result;

/**
 * 参数配置服务
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public interface ParamConfigService {

    /**
     * 根据paramNo获取paramValue(可考虑使用缓存)
     *
     * 返回值解析：如果是作为dubbo接口，即使数据项不存在也不应该返回success为false，
     * 提供方不做调用方的判断逻辑，只做数据查询工作
     *
     * @param paramNo 参数编码
     * @return 查询成功 - true | 查询失败 - false
     */
    Result<String> getByParamNo(String paramNo);

    /**
     * 根据paramNo更新paramValue
     *
     * @param paramNo 参数编码
     * @param paramValue 参数值
     * @return 处理成功 - true | 处理失败 - false
     */
    Result<String> updateByParamNo(String paramNo, String paramValue);

    /**
     * 在原值基础上追加paramValue
     *
     * @param paramNo 参数编码
     * @param paramValue 参数值
     * @return 追加成功 - true | 追加失败 - false
     */
    Result<String> appendTo(String paramNo, String paramValue);

    /**
     * 从原值中删除指定的paramValue
     *
     * @param paramNo 参数编码
     * @param paramValue 参数值
     * @return 删除成功 - true | 删除失败 - false
     */
    Result<String> removeSpecifiedContent(String paramNo, String paramValue);

    /**
     * 如果采用了缓存，可以调用该接口刷新缓存
     *
     * @param paramNo 参数编码
     * @return 刷新成功 - true | 刷新失败- false
     */
    Result<String> flushCache(String paramNo);
}
