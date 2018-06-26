package com.xujiangjun.archetype.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决前后端分离跨域问题
 * 设置跨域头代码必须放在doFilter之前，否则无效
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
public class CrossOriginFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin") == null ? "*" : request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request, response);
    }
}
