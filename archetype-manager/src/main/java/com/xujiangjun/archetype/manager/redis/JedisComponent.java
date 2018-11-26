package com.xujiangjun.archetype.manager.redis;

import com.xujiangjun.archetype.exception.BusinessException;
import com.xujiangjun.archetype.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.SafeEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Redis工具类
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
public class JedisComponent {

    /** 客户端分片连接池 **/
    private ShardedJedisPool pool;

    /** 序列化工具 **/
    private RedisSerializer serializer;

    /** key分隔符 **/
    private final static String KEY_SEPARATOR = "_";

    /** 同步锁前缀 **/
    private final static String SYNC_LOCK_PREFIX = "SYNC_";

    /** 封装Redis操作 **/
    private interface RedisCallback<T>{
        T doInRedis(ShardedJedis jedis);
    }

    /** 获得锁资源后，回调 **/
    public interface Callback<T>{
        T callback();
    }

    /** 统一获取和释放链接 **/
    private <T> T execute(RedisCallback<T> callback){
        ShardedJedis jedis = null;
        try {
            jedis = pool.getResource();
            return callback.doInRedis(jedis);
        } catch (Exception e) {
            log.error("invoke redis happens exception", e);
            throw new BusinessException(ResponseEnum.OPS_REDIS_ERROR, "调用redis处理失败");
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 使用Redis分布式锁保护代码（同步）
     *
     * @param key 需要锁的key
     * @param expires 过期时间（单位：秒）
     * @param callback 回调执行方法
     * @param <T> 泛型参数
     * @return 返回值
     */
    public <T> T sync(final String key, final int expires, Callback<T> callback){
        if(callback == null){
            throw new IllegalArgumentException();
        }
        final String lockKey = SYNC_LOCK_PREFIX + key;
        boolean locked = acquireLock(lockKey, expires);
        if(locked){
            try {
                return callback.callback();
            } finally {
                releaseLock(lockKey);
            }
        }
        return null;
    }

    /**
     * 为指定的key设置值, 如果key已经存储其他值, 就覆写旧值, 且无视类型。
     *
     * @param key key
     * @param value value
     */
    public void set(final String key, final Object value){
        this.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(ShardedJedis jedis) {
                return jedis.set(SafeEncoder.encode(key), serialize(value));
            }
        });
    }

    /**
     * 为指定的 key 设置值及其过期时间
     *
     * @param key key
     * @param value value
     * @param expire 过期时间(单位：秒)
     */
    public void set(final String key, final Object value, final int expire){
        this.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(ShardedJedis jedis) {
                return jedis.setex(SafeEncoder.encode(key), expire, serialize(value));
            }
        });
    }

    public boolean exists(final String key){
        return this.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(ShardedJedis jedis) {
                return jedis.exists(key);
            }
        });
    }

    public <T> T getSet(final String key, final Object value, final Class<T> clazz){
        return this.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(ShardedJedis jedis) {
                byte[] bytes = jedis.getSet(SafeEncoder.encode(key), serialize(value));
                return deserialize(bytes, clazz);
            }
        });
    }

    /**
     * 获取key对应的值，如果key不存在，返回null
     * 如果key储存的值不是字符串类型，返回一个错误
     *
     * @param key key
     * @param clazz 返回对象的类类型
     * @param <T> 泛型对象
     * @return key对应的值
     */
    public <T> T get(final String key, final Class<T> clazz){
        return this.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(ShardedJedis jedis) {
                byte[] bytes = jedis.get(SafeEncoder.encode(key));
                return deserialize(bytes, clazz);
            }
        });
    }

    /**
     * 获取key对应的值，value对应是一个list
     *
     * @param key key
     * @param clazz 元素类对象
     * @param <T> 泛型对象
     * @return list
     */
    public <T> List<T> getList(final String key, final Class<T> clazz) {
        return this.execute(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(ShardedJedis jedis) {
                byte[] bytes = jedis.get(SafeEncoder.encode(key));
                return deserializeToList(bytes, clazz);
            }
        });
    }

    public <T> List<T> hvals(final String key, final Class<T> clazz){
        return this.execute(new RedisCallback<List<T>>() {
            @Override
            public List<T> doInRedis(ShardedJedis jedis) {
                Collection<byte[]> value = jedis.hvals(SafeEncoder.encode(key));
                List<T> list = new ArrayList<T>(value.size());
                for (byte[] bytes : value) {
                    list.add(deserialize(bytes, clazz));
                }
                return list;
            }
        });
    }

    public void hset(final String key, final Object mapKey, final Object mapValue){
        this.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(ShardedJedis jedis) {
                return jedis.hset(SafeEncoder.encode(key), serialize(mapKey), serialize(mapValue));
            }
        });
    }

    public <T> T hget(final String key, final Object mapKey, final Class<T> clazz){
        return this.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(ShardedJedis jedis) {
                byte[] bytes = jedis.hget(SafeEncoder.encode(key), serialize(mapKey));
                return deserialize(bytes, clazz);
            }
        });
    }

    public Long del(final String key){
        return this.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(ShardedJedis jedis) {
                return jedis.del(key);
            }
        });
    }

    /**
     * 获取锁资源
     *
     * @param key key
     * @param expire 过期时间（单位：秒）
     * @return 获得锁 - true | 未获得锁 - false
     */
    public boolean acquireLock(final String key, final int expire) {
        return this.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(ShardedJedis jedis) {
                boolean locked = false;
                long expires = System.currentTimeMillis() + expire * 1000 + 1;
                long acquired = jedis.setnx(key, String.valueOf(expires));
                if (acquired > 0) {
                    jedis.expire(key, expire);
                    locked = true;
                } else {
                    // 当前锁过期时间
                    long oldValue = Long.valueOf(jedis.get(key));
                    // 当前锁已经超时
                    if (System.currentTimeMillis() > oldValue) {
                        // 查看是否有并发
                        String oldValueAgain = jedis.getSet(key, String.valueOf(expires));
                        // 获取锁成功
                        if (Long.valueOf(oldValueAgain) == oldValue) {
                            jedis.expire(key, expire);
                            locked = true;
                        }
                    }
                }
                return locked;
            }
        });
    }

    /**
     * 释放锁资源
     *
     * @param key key
     */
    public void releaseLock(final String key) {
        this.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(ShardedJedis jedis) {
                long expires = Long.valueOf(jedis.get(key));
                // 避免删除非自己获取得到的锁
                if (System.currentTimeMillis() < expires) {
                    jedis.del(key);
                }
                return null;
            }
        });
    }

    public void setPool(ShardedJedisPool pool) {
        this.pool = pool;
    }

    public void setSerializer(RedisSerializer serializer) {
        this.serializer = serializer;
    }

    public byte[] serialize(Object object){
        return serializer.serialize(object);
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz){
        return serializer.deserialize(bytes, clazz);
    }

    public <T> List<T> deserializeToList(byte[] bytes, Class<T> elementClazz){
        return serializer.deserializeToList(bytes, elementClazz);
    }

    public void destroy() {
        try {
            pool.destroy();
        } catch (Exception e) {
            log.error("redis连接放回连接池异常", e);
        }
    }
}
