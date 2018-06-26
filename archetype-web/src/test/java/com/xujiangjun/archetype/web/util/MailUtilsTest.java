package com.xujiangjun.archetype.web.util;

import com.xujiangjun.archetype.manager.util.MailUtils;
import com.xujiangjun.archetype.web.base.BaseTest;
import org.junit.Test;

import java.util.UUID;

/**
 * 邮件发送测试类
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public class MailUtilsTest extends BaseTest {

    @Test
    public void testSendActivateMail(){
        String code = UUID.randomUUID().toString().replace("-", "");
        MailUtils.sendActivateMail("xujiangjun@163.com", code);
    }
}
