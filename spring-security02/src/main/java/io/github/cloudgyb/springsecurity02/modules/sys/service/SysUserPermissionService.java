package io.github.cloudgyb.springsecurity02.modules.sys.service;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import io.github.cloudgyb.springsecurity02.common.utils.ServletUtils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

/**
 * 用户权限服务
 *
 * @author cloudgyb
 * 2021/9/13 21:34
 */
@Service("ups")
public class SysUserPermissionService {
    private final LoginUserService loginUserService;

    public SysUserPermissionService(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    public boolean hasPermission() {
        final HttpServletRequest request = ServletUtils.getRequest();
        if(request == null)
            throw new AccessDeniedException("非法调用！");
        final String servletPath = request.getServletPath();
        final Set<String> allPermissions = loginUserService.getAllPermissions();
        return allPermissions.contains(servletPath);
    }
}
