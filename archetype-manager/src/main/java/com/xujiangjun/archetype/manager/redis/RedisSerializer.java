package com.xujiangjun.archetype.manager.redis;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Redis序列化工具类
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public interface RedisSerializer {

    Charset CHARSET = Charset.forName("UTF-8");

    byte[] serialize(Object object);

    <T> T deserialize(byte[] bytes, Class<T> clazz);

    <T> List<T> deserializeToList(byte[] bytes, Class<T> elementClazz);
}
