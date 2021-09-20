package io.github.cloudgyb.springsecurity02.modules.sys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cloudgyb.springsecurity02.common.po.ApiResult;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

/**
 * 用户登出服务
 *
 * @author cloudgyb
 * 2021/9/5 20:03
 */
@Service
public class SysUserLogoutService implements LogoutSuccessHandler {
    private final TokenService tokenService;

    public SysUserLogoutService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        tokenService.cleanToken(request);
        ApiResult res = ApiResult.ok("登出成功！");
        final ObjectMapper mapper = new ObjectMapper();
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE+";charset=utf-8");
            response.getWriter().write(mapper.writeValueAsString(res));
            response.flushBuffer();
        }
        catch (Exception ignore) {
        }
    }
}
