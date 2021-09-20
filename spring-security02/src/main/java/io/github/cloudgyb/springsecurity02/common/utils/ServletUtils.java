package io.github.cloudgyb.springsecurity02.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Servlet 工具类
 *
 * @author cloudgyb
 * 2021/9/8 21:59
 */
public final class ServletUtils {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null)
            return servletRequestAttributes.getRequest();
        return null;
    }
}
