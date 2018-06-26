package com.xujiangjun.archetype.web.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * web层测试基类
 * @author xujiangjun
 * @since 2018.05.20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:archetype-context.xml"})
@Transactional
public class BaseTest {

}
