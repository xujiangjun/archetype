package com.xujiangjun.archetype.web.util;

import com.xujiangjun.archetype.manager.redis.JedisComponent;
import com.xujiangjun.archetype.web.base.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author xujiangjun
 * @since 2018.05.20
 */
public class JedisComponentTest extends BaseTest {

    @Autowired
    private JedisComponent jedisComponent;

    @Test
    public void testSet(){
        jedisComponent.set("key", "Hello");
    }

    @Test
    public void testGet(){
        String value = jedisComponent.get("key", String.class);
        System.out.println(value);
    }

    @Test
    public void testGetSet(){
        Integer oldValue = jedisComponent.getSet("key", "modal", Integer.class);
        System.out.println(oldValue);
    }

    @Test
    public void testDel(){
        jedisComponent.del("key");
    }

    @Test
    public void testSetList(){
        List<String> strList = new ArrayList<>();
        strList.add("first");
        strList.add("second");
        jedisComponent.set("key", strList);
    }

    @Test
    public void testGetList(){
        List<String> value = jedisComponent.getList("key", String.class);
        System.out.println(value);
    }

    @Test
    public void testAcquireLock(){
        boolean locked = jedisComponent.acquireLock("key", 120);
        System.out.println(locked);
    }

    @Test
    public void testReleaseLock(){
        jedisComponent.releaseLock("key");
    }

    @Test
    public void testSync(){
        String result = jedisComponent.sync("key", 120, new JedisComponent.Callback<String>() {
            @Override
            public String callback() {
                return "Result:" + new Random().nextInt(100);
            }
        });
        System.out.println(result);
    }
}
