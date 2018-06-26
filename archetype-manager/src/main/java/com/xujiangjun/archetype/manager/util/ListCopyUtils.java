package com.xujiangjun.archetype.manager.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * List对象拷贝，使用BeanUtils
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
public class ListCopyUtils {

    /**
     * List之间的拷贝
     *
     * @param list 源对象列表
     * @param targetClass 目标对象Class
     * @param <T> 泛型 - 代表目标对象
     * @return T类型对象列表
     */
    public static <T> List<T> copyTo(List<?> list, Class<T> targetClass){
        List<T> targetList = new ArrayList<>();
        if(CollectionUtils.isEmpty(list)){
            return targetList;
        }
        try {
            for (Object obj : list) {
                T targetObj = targetClass.newInstance();
                BeanUtils.copyProperties(obj, targetObj);
                targetList.add(targetObj);
            }
        } catch (InstantiationException e) {
            log.error("ListCopyUtils.copyTo() happens instantiationException. list:{}, targetClass:{}",
                    list, targetClass, e);
            throw new RuntimeException("happens instantiationException", e);
        } catch (IllegalAccessException e) {
            log.error("ListCopyUtils.copyTo() happens illegalAccessException. list:{}, targetClass:{}",
                    list, targetClass, e);
            throw new RuntimeException("happens illegalAccessException", e);
        }
        return targetList;
    }
}
