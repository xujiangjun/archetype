package com.xujiangjun.archetype.manager.util;

import com.xujiangjun.archetype.exception.BusinessException;
import com.xujiangjun.archetype.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期工具类
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
public class DateUtils {
    public static final String FORMAT_STR_END_MONTH = "yyyy-MM";
    public static final String FORMAT_STR_END_DAY = "yyyy-MM-dd";
    public static final String FORMAT_STR_END_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_STR_END_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static DateTimeFormatter defaultFormatter = DateTimeFormat.forPattern(FORMAT_STR_END_SECOND);

    public static String getPattern(String str) {
        String pattern;
        switch (str.length()){
            case 7:
                pattern = FORMAT_STR_END_MONTH;
                break;
            case 10:
                pattern = FORMAT_STR_END_DAY;
                break;
            case 16:
                pattern = FORMAT_STR_END_MINUTE;
                break;
            case 19:
                pattern = FORMAT_STR_END_SECOND;
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("字段值：").append(str).append("不支持日期格式化");
                log.warn(sb.toString());
                throw new BusinessException(ResponseEnum.DATE_FORMAT_ERROR, sb.toString());
        }
        return pattern;
    }

}
