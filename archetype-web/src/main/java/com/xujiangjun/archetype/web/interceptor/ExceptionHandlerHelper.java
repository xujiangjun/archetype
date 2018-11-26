package com.xujiangjun.archetype.web.interceptor;

import com.xujiangjun.archetype.support.Result;
import com.xujiangjun.archetype.exception.BusinessException;
import com.xujiangjun.archetype.enums.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * Controller 异常处理
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class ExceptionHandlerHelper {

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e, HttpServletRequest request) {
        printParameter(request);
        if (e instanceof BusinessException) {
            log.error("【Http请求】抛出自定义异常", e);
            BusinessException exception = (BusinessException) e;
            return Result.wrapFailureResult(exception.getCode(), exception.getMessage());
        } else if(e instanceof DataAccessException){
            log.error("【Http请求】抛出数据访问异常", e);
            return Result.wrapFailureResult(ResponseEnum.SYSTEM_ERROR);
        } else {
            log.error("【Http请求】抛出未知异常，msg:", e);
            return Result.wrapFailureResult(ResponseEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 打印请求参数，不能打印post请求中的body内容。(因为是从InputStream中读取的，SpringMVC已经读取过，流已关闭。)
     *
     * @param request
     */
    private void printParameter(HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap.size() > 0) {
                StringBuilder sb = new StringBuilder("Controller捕获异常，打印请求参数。");
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    sb.append(entry.getKey()).append(":").append(Arrays.toString(entry.getValue())).append(", ");
                }
                String parameter = sb.substring(0, sb.lastIndexOf(","));
                log.error(parameter);
            }
        } catch (Exception e) {
            log.error("异常拦截器打印请求参数异常", e);
        }
    }
}
