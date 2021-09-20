package io.github.cloudgyb.springsecurity02.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cloudgyb.springsecurity02.common.po.ApiResult;
import io.github.cloudgyb.springsecurity02.modules.sys.service.SysUserLogoutService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * @author cloudgyb
 * 2021/7/11 15:04
 */
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {
    private JdbcUserDetailService userDetailService;
    private SysUserLogoutService logoutSuccessHandler;
    private final JwtFilter jwtFilter;

    public HttpSecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    private final AccessDeniedHandler accessDeniedHandle = (request, response, e) -> {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8");
        final ApiResult error = ApiResult.error(HttpStatus.FORBIDDEN.value(),"用户登录过期！");
        ObjectMapper mapper = new ObjectMapper();
        final String s = mapper.writeValueAsString(error);
        response.getWriter().write(s);
        response.flushBuffer();
    };

    private final AuthenticationEntryPoint authenticationEntryPoint = (request, response, e) -> {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8");
        final ApiResult error = ApiResult.error(HttpStatus.FORBIDDEN.value(),"用户未登录！");
        ObjectMapper mapper = new ObjectMapper();
        final String s = mapper.writeValueAsString(error);
        response.getWriter().write(s);
        response.flushBuffer();
    };

    @Autowired
    public void userDetailService(JdbcUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Autowired
    public void logoutSuccessHandler(SysUserLogoutService logoutService){
        this.logoutSuccessHandler = logoutService;
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/error").anonymous()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
        //用户登录过期处理
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandle);
        //用户未认证(未登录)处理
        httpSecurity.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
}
