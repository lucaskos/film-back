package com.luke.filmdb.config.token;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Luke on 24.10.2018.
 */
@Data
public class TokenProperties {

    @Value("${security.token.maxAgeSeconds}")
    private long maxAgeSeconds;
    @Value("${security.token.secret}")
    private static String secret;

    public TokenProperties(long maxAgeSeconds, String secret) {
        this.maxAgeSeconds = maxAgeSeconds;
        TokenProperties.secret = secret;
    }

    public TokenProperties() {
    }

    public long getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    public void setMaxAgeSeconds(long maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }

    public static String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        TokenProperties.secret = secret;
    }
}
