package com.tunehub.config;

import com.tunehub.interceptor.AuthenticationInterceptor;
import jakarta.servlet.SessionTrackingMode;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * Web configuration for session management and security settings.
 * Disables URL rewriting to prevent JSESSIONID from appearing in URLs.
 * Registers authentication interceptor for protected routes.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    public WebConfig(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    /**
     * Configures session tracking to use cookies only, preventing JSESSIONID
     * from appearing in URLs through URL rewriting.
     *
     * @return ServletContextInitializer that sets session tracking mode to COOKIE only
     */
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            // Force cookie-only session tracking, disable URL rewriting
            servletContext.setSessionTrackingModes(
                Collections.singleton(SessionTrackingMode.COOKIE)
            );
        };
    }

    /**
     * Register interceptors for authentication and authorization
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/users/**", "/admin/**")
                .excludePathPatterns("/login", "/register", "/logout", "/error",
                                   "/static/**", "/css/**", "/js/**", "/images/**",
                                   "/avatars/**", "/fonts/**");
    }

    /**
     * Configure static resource handlers
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ensure static resources are properly mapped
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");

        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

        registry.addResourceHandler("/avatars/**")
                .addResourceLocations("classpath:/static/avatars/");

        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/");

        // Map root-level static files
        registry.addResourceHandler("/*.css", "/*.js")
                .addResourceLocations("classpath:/static/");
    }
}


