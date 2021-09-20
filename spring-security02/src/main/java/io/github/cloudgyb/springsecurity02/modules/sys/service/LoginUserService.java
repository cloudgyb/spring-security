package io.github.cloudgyb.springsecurity02.modules.sys.service;

import java.util.Collections;
import java.util.Set;

import com.mysql.cj.log.Log;
import io.github.cloudgyb.springsecurity02.config.LoginUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 登录用户服务
 *
 * @author cloudgyb
 * 2021/9/13 21:36
 */
@Service
public class LoginUserService {

    public LoginUserDetails getLoginUserDetails() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (LoginUserDetails) authentication.getPrincipal();
    }

    public Set<String> getAllPermissions() {
        final LoginUserDetails loginUserDetails = getLoginUserDetails();
        if(loginUserDetails == null)
            return Collections.emptySet();
        return loginUserDetails.getPermissions();
    }
}
