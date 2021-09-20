package io.github.cloudgyb.springsecurity02.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.cloudgyb.springsecurity02.modules.sys.service.TokenService;
import io.jsonwebtoken.Claims;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Json Web Token 过滤器
 *
 * @author cloudgyb
 * 2021/9/11 16:56
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    public JwtFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization");
        final String servletPath = request.getServletPath();
        if (!"/login".equals(servletPath) && StringUtils.hasText(token)) {
            final Claims claims = tokenService.parseToken(token);
            if (claims != null) {
                final String username = claims.getSubject();
                final LoginUserDetails loginUserDetails = tokenService.getLoginUserDetails(username);
                if (loginUserDetails != null) {
                    loginUserDetails.addRoles(loginUserDetails.getRoles());
                    loginUserDetails.addPermissions(loginUserDetails.getPermissions());
                    final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            loginUserDetails, loginUserDetails.getPassword(), loginUserDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    tokenService.flushToken(loginUserDetails);
                }
            }

        }
        filterChain.doFilter(request, response);
    }
}
