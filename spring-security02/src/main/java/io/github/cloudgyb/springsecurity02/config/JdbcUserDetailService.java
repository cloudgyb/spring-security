package io.github.cloudgyb.springsecurity02.config;

import java.util.List;

import io.github.cloudgyb.springsecurity02.modules.sys.entity.SysUser;
import io.github.cloudgyb.springsecurity02.modules.sys.service.SysUserRoleService;
import io.github.cloudgyb.springsecurity02.modules.sys.service.SysUserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author cloudgyb
 * 2021/9/4 18:12
 */

@Component
public class JdbcUserDetailService implements UserDetailsService {
    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;

    public JdbcUserDetailService(SysUserService sysUserService, SysUserRoleService sysUserRoleService) {
        this.sysUserService = sysUserService;
        this.sysUserRoleService = sysUserRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.getUserByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户或密码错误！");
        }
        Integer userId = user.getId();
        Byte status = user.getStatus();
        LoginUserDetails loginUser = LoginUserDetails.build()
                .userId(userId + "")
                .username(username)
                .password(user.getPassword())
                .enable(status != 0)
                .isAccountNonLocked(status != 2)
                .isCredentialsNonExpired(status != 3)
                .isAccountNonExpired(status != 4);
        List<String> roles = sysUserRoleService.getUserRolesStrByUserId(userId);
        loginUser.addRoles(roles);
        return loginUser;
    }
}
