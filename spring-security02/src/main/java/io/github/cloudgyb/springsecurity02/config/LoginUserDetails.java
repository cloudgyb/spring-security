package io.github.cloudgyb.springsecurity02.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 登录用户详情
 *
 * @author cloudgyb
 * 2021/9/4 20:40
 */
public class LoginUserDetails implements UserDetails {
    private static final String rolePrefix = "ROLE_";

    private String userId;
    private String username;
    private String password;
    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;

    /**
     * 用户角色
     */
    @JsonIgnore
    private final Set<GrantedAuthority> grantedRoles = new HashSet<>();
    /**
     * 用户角色字符串类型视图
     */
    private final Set<String> roles = new HashSet<>();
    /**
     * 用户权限
     */
    private final Set<GrantedAuthority> grantedPermissions = new HashSet<>();
    /**
     * 字符串类型用户权限的视图
     */
    private final Set<String> permissions = new HashSet<>();


    public static LoginUserDetails build() {
        return new LoginUserDetails();
    }

    public LoginUserDetails userId(String userId) {
        this.userId = userId;
        return this;
    }

    public LoginUserDetails username(String username) {
        this.username = username;
        return this;
    }

    public LoginUserDetails password(String password) {
        this.password = password;
        return this;
    }

    public LoginUserDetails enable(boolean enable) {
        this.enabled = enable;
        return this;
    }

    public LoginUserDetails isCredentialsNonExpired(boolean isCredentialsNonExpired) {
        this.credentialsNonExpired = isCredentialsNonExpired;
        return this;
    }

    public LoginUserDetails isAccountNonExpired(boolean isAccountNonExpired) {
        this.accountNonExpired = isAccountNonExpired;
        return this;
    }

    public LoginUserDetails isAccountNonLocked(boolean isAccountNonLocked) {
        this.accountNonLocked = isAccountNonLocked;
        return this;
    }

    @JsonIgnore
    @Override
    public Set<GrantedAuthority> getAuthorities() {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(grantedRoles);
        authorities.addAll(grantedPermissions);
        return authorities;
    }

    public void addRoles(String... roles) {
        if (roles == null || roles.length == 0)
            return;
        final Set<String> roleList = new HashSet<>(Arrays.asList(roles));
        this.addRoles(roleList);
    }

    public void addRoles(Collection<String> roles) {
        if (roles == null || roles.size() == 0) {
            return;
        }
        this.roles.addAll(roles);
        final List<GrantedAuthority> authorities = roles.stream()
                .map(e -> new SimpleGrantedAuthority(rolePrefix + e))
                .collect(Collectors.toList());
        this.grantedRoles.addAll(authorities);
    }

    public void addPermissions(String... permissions) {
        if (permissions == null || permissions.length == 0)
            return;
        final Set<String> permissionList = new HashSet<>(Arrays.asList(permissions));
        this.addPermissions(permissionList);
    }

    public void addPermissions(Collection<String> permissions) {
        if (permissions == null || permissions.size() == 0)
            return;
        this.permissions.addAll(permissions);
        final Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        this.grantedPermissions.addAll(authorities);
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
