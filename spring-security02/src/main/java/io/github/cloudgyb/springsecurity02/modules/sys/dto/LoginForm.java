package io.github.cloudgyb.springsecurity02.modules.sys.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 登录表单
 *
 * @author cloudgyb
 * 2021/9/5 20:30
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    @NotBlank(message = "用户名不能为空！")
    private String username;
    @NotBlank(message = "密码不能为空！")
    private String password;
    private String uuid;
    private String captcha;
}
