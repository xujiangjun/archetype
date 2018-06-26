package com.xujiangjun.archetype.web.controller.base;

import com.xujiangjun.archetype.support.Result;
import com.xujiangjun.archetype.service.base.ParamConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 参数配置Controller
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
@RestController
@RequestMapping("param")
public class ParamConfigController {

    @Autowired
    private ParamConfigService paramConfigService;

    @RequestMapping("")
    public Result<String> config(){
        return Result.wrapSuccessfulResult("Hello");
    }

}
