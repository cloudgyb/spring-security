package io.github.cloudgyb.springsecurity02.modules.sys.service;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import io.github.cloudgyb.springsecurity02.common.constants.CacheKeyConstants;
import io.github.cloudgyb.springsecurity02.common.constants.CommonConstants;
import io.github.cloudgyb.springsecurity02.config.LoginUserDetails;
import io.github.cloudgyb.springsecurity02.modules.sys.config.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * token 服务
 *
 * @author cloudgyb
 * 2021/9/8 19:01
 */
@Service
public class TokenService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TokenProperties tokenProperties;
    private final RedisTemplate<String, Object> redisTemplate;

    public TokenService(TokenProperties tokenProperties,
            RedisTemplate<String, Object> redisTemplate) {
        this.tokenProperties = tokenProperties;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 签发token
     * @param loginUser 签发的用户
     * @return token
     */
    public String issueToken(LoginUserDetails loginUser) {
        cacheAuthentication(loginUser);
        final SignatureAlgorithm algorithm = tokenProperties.getAlgorithm();
        final String secretKey = tokenProperties.getSecretKey();
        final int expireInSeconds = tokenProperties.getExpireInSeconds();
        final Date issueDate = new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.SECOND, expireInSeconds);
        Date expireDate = new Date(calendar.getTimeInMillis());
        final Claims claims = new DefaultClaims()
                .setId(loginUser.getUserId())
                .setIssuer("geng") //token签发者
                .setSubject(loginUser.getUsername()) //token签发给
                .setIssuedAt(issueDate)//token签发时间
                .setExpiration(expireDate) //token过期时间
                ;
        return Jwts.builder()
                .setClaims(claims)
                .signWith(algorithm, secretKey)
                .compact();
    }

    private void cacheAuthentication(LoginUserDetails loginUser) {
        String username = loginUser.getUsername();
        redisTemplate.opsForValue()
                .set(CacheKeyConstants.authorization_info_prefix + username,
                        loginUser, tokenProperties.getExpireInSeconds(), TimeUnit.SECONDS);
    }

    /**
     * 从token解析出Json web token对象
     * 如果解析成功且token未过期返回{@link Claims}对象，否则返回null
     * @param token token签名
     * @return 失败返回null
     */
    public Claims parseToken(String token) {
        try {
            final Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(tokenProperties.getSecretKey())
                    .parseClaimsJws(token);
            return claimsJws.getBody();
        }
        catch (JwtException e) {
            logger.error(String.format("token '%s' 无效！", token) + e.getLocalizedMessage());
        }
        return null;
    }

    public LoginUserDetails getLoginUserDetails(String username) {
        return (LoginUserDetails) redisTemplate.opsForValue()
                .get(CacheKeyConstants.authorization_info_prefix + username);
    }

    public void cleanToken(HttpServletRequest request) {
        final String token = request.getHeader(CommonConstants.HTTP_Authorization_HEADER);

        if (StringUtils.hasText(token)) {
            final Claims claims = parseToken(token);
            if (claims == null)
                return;
            final String username = claims.getSubject();
            redisTemplate.delete(CacheKeyConstants.authorization_info_prefix + username);
        }
    }

    public void flushToken(LoginUserDetails loginUserDetails) {
        final String username = loginUserDetails.getUsername();
        redisTemplate.opsForValue()
                .set(CacheKeyConstants.authorization_info_prefix + username,
                        loginUserDetails,
                        tokenProperties.getExpireInSeconds(),
                        TimeUnit.SECONDS);
    }
}
