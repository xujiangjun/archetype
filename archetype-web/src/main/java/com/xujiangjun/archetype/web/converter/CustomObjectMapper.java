package com.xujiangjun.archetype.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.xujiangjun.archetype.exception.BusinessException;
import com.xujiangjun.archetype.enums.ResponseEnum;
import com.xujiangjun.archetype.manager.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.Date;

/**
 * 自定义返回json字符串
 * MappingJackson2HttpMessageConverter默认将Date类型数据转换为long类型，此处自定义Date -> String
 *
 * @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")放在对应的字段属性上与此类效果相同，但是需要频繁使用
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        SimpleModule module = new SimpleModule();
        // JSON序列化返回的字符串时，Date -> String
        module.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException, JsonProcessingException {
                gen.writeString(new DateTime(value).toString("yyyy-MM-dd HH:mm:ss"));
            }
        });
        // JSON反序列化为对象时，String -> Date
        module.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException, JsonProcessingException {
                String pattern = getPattern(jsonParser);
                DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
                return formatter.parseDateTime(jsonParser.getText()).toDate();
            }
        });
        this.registerModule(module);
    }

    private String getPattern(JsonParser jsonParser) throws IOException {
        String pattern;
        switch (jsonParser.getText().length()){
            case 7:
                pattern = DateUtils.FORMAT_STR_END_MONTH;
                break;
            case 10:
                pattern = DateUtils.FORMAT_STR_END_DAY;
                break;
            case 16:
                pattern = DateUtils.FORMAT_STR_END_MINUTE;
                break;
            case 19:
                pattern = DateUtils.FORMAT_STR_END_SECOND;
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append(jsonParser.getCurrentName()).append("的字段值是：").append(jsonParser.getText())
                        .append("不支持日期格式化");
                log.warn(sb.toString());
                throw new BusinessException(ResponseEnum.DATE_FORMAT_ERROR, sb.toString());
        }
        return pattern;
    }
}
