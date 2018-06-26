package com.xujiangjun.archetype.manager.util;

import com.google.common.base.Splitter;

/**
 * 校验ip是否符合规则
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public class IpCheckUtils {

    /**
     * 支持精确匹配和通配符*匹配
     *
     * @param accessIp 客户端访问的ip
     * @return 是否允许访问
     */
    public static boolean checkIp(String accessIp, String paramValue){
        Iterable<String> allowIpList = Splitter.on(",").omitEmptyStrings().trimResults().split(paramValue);
        int matches = 0;
        for (String allowIp : allowIpList) {
            // 1.0 通过匹配
            if(matches > 0){
                return true;
            }
            // 2.1 精确匹配
            if(allowIp.trim().equals(accessIp)){
                matches++;
                break;
            }
            // 2.2 通配符匹配
            matches = getMatchesByBits(accessIp, matches, allowIp);
        }
        // 3.0 通过匹配
        if(matches > 0){
            return true;
        }
        return false;
    }

    /**
     * 通配符匹配 -> 按位匹配
     *
     * @param accessIp 客户端访问的ip
     * @param matches  > 0 表示已通过匹配
     * @param allowIp  配置文件中配置的IP白名单
     * @return > 0 表示已通过匹配
     */
    public static int getMatchesByBits(String accessIp, int matches, String allowIp) {
        if(allowIp.contains("*")){
            String[] accessIpBits = accessIp.split("\\.");
            String[] allowIpBits = allowIp.split("\\.");
            int length = accessIpBits.length;
            int subMatches = 0;
            for (int i = 0; i < length; i++) {
                if("*".equals(allowIpBits[i]) || allowIpBits[i].trim().equals(accessIpBits[i])){
                    subMatches++;
                    continue;
                }
                break;
            }
            if(subMatches == 4){
                matches++;
            }
        }
        return matches;
    }
}
