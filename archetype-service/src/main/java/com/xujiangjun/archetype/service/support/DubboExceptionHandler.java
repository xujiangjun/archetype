package com.xujiangjun.archetype.service.support;

import com.alibaba.fastjson.JSONArray;
import com.xujiangjun.archetype.api.support.RpcResult;
import com.xujiangjun.archetype.enums.ErrorEnum;
import com.xujiangjun.archetype.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.dao.DataAccessException;

import java.lang.reflect.Method;

/**
 * Dubbo异常拦截器
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
public class DubboExceptionHandler implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (BusinessException e) {
            log.error("Dubbo服务抛出自定义异常", e);
            return processException(invocation, e);
        } catch (DataAccessException e) {
            log.error("Dubbo服务抛出数据访问异常", e);
            return processException(invocation, e);
        } catch (Exception e) {
            log.error("Dubbo服务抛出未知异常", e);
            return processException(invocation, e);
        }
    }

    private Object processException(MethodInvocation invocation, Exception e) {
        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();
        String methodName = method.getDeclaringClass().getName() + "." + method.getName();
        log.error("Dubbo服务异常[method = " + methodName + ", params = " + JSONArray.toJSONString(args) + "]", e);
        Class<?> clazz = method.getReturnType();
        if (clazz.equals(RpcResult.class)) {
            if (e instanceof BusinessException) {
                BusinessException ex = (BusinessException) e;
                return RpcResult.wrapFailureResult(ex.getCode(), ex.getMessage());
            }
            return RpcResult.wrapFailureResult(ErrorEnum.SYSTEM_ERROR.getCode(),
                    ErrorEnum.SYSTEM_ERROR.getMessage());
        }
        return null;
    }
}
