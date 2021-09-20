package io.github.cloudgyb.springsecurity02.modules.sys.config;

import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * java web token 属性配置
 *
 * @author cloudgyb
 * 2021/9/8 19:03
 */
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
    private String secretKey;
    private int expireInSeconds;
    private SignatureAlgorithm algorithm;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getExpireInSeconds() {
        return expireInSeconds;
    }

    public void setExpireInSeconds(int expireInSeconds) {
        this.expireInSeconds = expireInSeconds;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(SignatureAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
