package io.github.cloudgyb.springsecurity01.config;

import java.util.HashSet;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author cloudgyb
 * 2021/7/11 18:12
 */

@Component
public class WebSecurityUserDetailService implements UserDetailsService {
    private final BCryptPasswordEncoder passwordEncoder;

    public WebSecurityUserDetailService(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (userName.equals("geng")) {
            final HashSet<SimpleGrantedAuthority> roleAndAuthority = new HashSet<>();
            roleAndAuthority.add(new SimpleGrantedAuthority("hello"));
            roleAndAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
            return User.withUsername(userName)
                    .password(passwordEncoder.encode("geng"))
                    .authorities(roleAndAuthority)
                    .build();
        }
        throw new UsernameNotFoundException("用户或密码错误！");
    }
}
