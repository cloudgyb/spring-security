package io.github.cloudgyb.springsecurity02.modules.sys.service;

import io.github.cloudgyb.springsecurity02.config.LoginUserDetails;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 用户登录服务
 *
 * @author cloudgyb
 * 2021/9/5 20:03
 */
@Service
public class SysUserLoginService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public SysUserLoginService(AuthenticationManager authenticationManager,
            TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String login(String username,String password,String captchaCode){
        Authentication authentication = new UsernamePasswordAuthenticationToken(username,password);
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final LoginUserDetails loginUser = (LoginUserDetails) authentication.getPrincipal();
        //登录成功颁发token
        return tokenService.issueToken(loginUser);
    }


}
