package com.tunehub.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration properties.
 * Maps properties from application.properties with prefix "app".
 */
@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

    private Security security = new Security();
    private Session session = new Session();
    private Upload upload = new Upload();

    @Getter
    @Setter
    public static class Security {
        private int bcryptStrength = 12;
        private int maxLoginAttempts = 5;
        private long loginAttemptResetMinutes = 15;
    }

    @Getter
    @Setter
    public static class Session {
        private int timeoutMinutes = 30;
        private boolean httpOnly = true;
        private boolean secure = false; // Set to true in production with HTTPS
    }

    @Getter
    @Setter
    public static class Upload {
        private String maxFileSize = "15MB";
        private String maxRequestSize = "15MB";
        private String avatarPath = "uploads/avatars/";
        private String musicPath = "uploads/music/";
    }
}

