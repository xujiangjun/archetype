package com.xujiangjun.archetype.manager.redis;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import redis.clients.util.SafeEncoder;

import java.util.List;

/**
 * JSON序列化器
 *
 * @author xujiangjun
 * @since 2018.05.20
 *
 * http://blog.csdn.net/u010246789/article/details/52539576
 */
@Slf4j
public class JSONSerializer implements RedisSerializer {

    /** Date使用ISO8601格式输出 **/
    private static final SerializerFeature[] FEATURES =
            {SerializerFeature.UseISO8601DateFormat, SerializerFeature.WriteClassName};

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return SafeEncoder.encode(JSONObject.toJSONString(object, FEATURES));
        } catch (Exception e) {
            log.error("invoke JSONSerializer.serialize() fail", e);
            return null;
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return JSONObject.parseObject(SafeEncoder.encode(bytes), clazz);
        } catch (Exception e) {
            log.error("invoke JSONSerializer.deserialize() fail", e);
            return null;
        }
    }

    @Override
    public <T> List<T> deserializeToList(byte[] bytes, Class<T> elementClazz) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return JSONObject.parseArray(SafeEncoder.encode(bytes), elementClazz);
        } catch (Exception e) {
            log.error("invoke JSONSerializer.deserializeToList() fail", e);
            return null;
        }
    }
}
