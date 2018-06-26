package com.xujiangjun.archetype.web.filter;

import com.xujiangjun.archetype.constant.ConfigConsts;
import com.xujiangjun.archetype.support.Result;
import com.xujiangjun.archetype.manager.util.IpCheckUtils;
import com.xujiangjun.archetype.manager.util.StringUtils;
import com.xujiangjun.archetype.service.base.ParamConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用于控制ip白名单访问
 *
 * @author xujiangjun
 * @since 2018.05.20
 */
@Slf4j
public class AccessFilter extends OncePerRequestFilter {

    private ParamConfigService paramConfigService;

    /**
     * 通过注解@Autowired无法注入，需要采用该方式注入
     *
     * @throws ServletException
     */
    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getWebApplicationContext(this.getServletContext());
        paramConfigService = webApplicationContext.getBean(ParamConfigService.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String ip = getLocalIp(httpServletRequest);
        Result<String> result = paramConfigService.getByParamNo(ConfigConsts.IP_WHITE_LIST);
        if(result.isSuccess() && IpCheckUtils.checkIp(ip, result.getData())){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = httpServletResponse.getWriter();
        if(!result.isSuccess()){
            log.error("未配置IP白名单，请前往配置IP白名单");
            out.append("{\"msg\":\"未配置IP白名单，请前往配置IP白名单！\"}");
        }else{
            out.append("{\"msg\":\"您无权访问，请申请权限访问！\", \"ip\":\"").append(ip).append("\"}");
        }
        if(out != null){
            out.close();
        }
    }

    /**
     * 获取客户端真实ip
     * 1.如果X-Forwarded-For不为空，拿XFF的左边第一个
     * 2.如果X-Forwarded-For为空，拿X-Real-IP
     * 3.如果X-Real-IP为空，只能拿request.getRemoteAddr()
     * http://www.cnblogs.com/ITtangtang/p/3927768.html
     *
     * @param request HttpServletRequest
     * @return 客户端真实ip
     */
    private String getLocalIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
