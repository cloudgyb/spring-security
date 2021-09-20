package io.github.cloudgyb.springsecurity02.modules.sys.controller;

import io.github.cloudgyb.springsecurity02.common.po.ApiResult;
import io.github.cloudgyb.springsecurity02.modules.sys.dto.LoginForm;
import io.github.cloudgyb.springsecurity02.modules.sys.service.SysUserLoginService;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口
 *
 * @author cloudgyb
 * 2021/9/4 21:46
 */
@RestController
public class LoginController {
    private final SysUserLoginService sysUserLoginService;

    public LoginController(SysUserLoginService sysUserLoginService) {
        this.sysUserLoginService = sysUserLoginService;
    }

    @PostMapping("/login")
    public ApiResult login(@RequestBody @Validated LoginForm form){
        String token = sysUserLoginService.login(form.getUsername(), form.getPassword(), form.getCaptcha());
        return ApiResult.ok((Object) token);
    }
}
