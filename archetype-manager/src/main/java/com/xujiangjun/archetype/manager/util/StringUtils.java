package com.xujiangjun.archetype.manager.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import java.util.*;

/**
 * 字符串工具类
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param str 需要判断的字符串
     * @return 字符串为空 - true | 字符串不为空 - false
     */
    public static boolean isEmpty(final String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否非空
     *
     * @param str 需要判断的字符串
     * @return 字符串不为空 - true | 字符串为空 - false
     */
    public static boolean isNotEmpty(final String str){
        return !isEmpty(str);
    }

    /**
     * 判断str是否包含在complexStr中
     *
     * @param str 需要判断的字符串
     * @param complexStr 用分隔符分隔的字符串
     * @return 包含 - true | 不包含 - false
     */
    public static boolean contains(String str, String complexStr) {
        if (isEmpty(str) || isEmpty(complexStr)) {
            return false;
        }
        Iterable<String> it = Splitter.on(",").omitEmptyStrings().trimResults().split(complexStr);
        for (String s : it) {
            if (Objects.equals(s, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果complexStr中包含str，则删除
     *
     * @param str 需要判断的字符串
     * @param complexStr 用分隔符分隔的字符串
     * @return 删除str后的字符串
     */
    public static String removeContains(String str, String complexStr) {
        // 此处不进行参数校验，在调用处进行参数校验并返回相应的错误信息
        List<String> list = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(complexStr);
        List<String> strList = new ArrayList<>(list);
        Iterator<String> iterator = strList.iterator();
        while (iterator.hasNext()) {
            String partStr = iterator.next();
            if (Objects.equals(partStr, str)) {
                iterator.remove();
            }
        }
        return Joiner.on(",").skipNulls().join(strList);
    }

    /**
     * 将一个复杂的字符串转换成map(先按","处理，再按":"处理，最后返回map)
     *
     * @param complexStr 数据形式如："a:12,,b:23,     c3:24"
     * @return 冒号前的值为key，冒号后的值为value
     */
    public static Map<String, String> getMapFromComplexStr(String complexStr){
        if(isEmpty(complexStr)){
            return new HashMap<>();
        }
        Map<String,String> map = Splitter.on(",").omitEmptyStrings().trimResults()
                .withKeyValueSeparator(":").split(complexStr);
        return map;
    }

}
