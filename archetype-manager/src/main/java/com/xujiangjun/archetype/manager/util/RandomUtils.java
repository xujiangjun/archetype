package com.xujiangjun.archetype.manager.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具类
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public class RandomUtils {

    private static final ThreadLocalRandom THREAD_LOCAL_RANDOM = ThreadLocalRandom.current();

    /**
     * 产生随机整数[least, bound)
     *
     * @param least 最小整数值
     * @param bound 最大边界值
     * @return 随机整数
     */
    public static int nextInt(int least, int bound){
        return THREAD_LOCAL_RANDOM.nextInt(least, bound);
    }

}
