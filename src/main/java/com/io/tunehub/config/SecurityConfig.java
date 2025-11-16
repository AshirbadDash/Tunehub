//package com.io.tunehub.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.disable())
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()
//                )
//                .formLogin(form -> form.disable())
//                .httpBasic(basic -> basic.disable());
//
//        return http.build();
//    }
//
/// /    @Bean
/// /    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
/// /
/// /        http
/// /                // ENABLE CORS HERE
/// /                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
/// /
/// /                // Disable built-in login page
/// /                .formLogin(form -> form.disable())
/// /                .httpBasic(basic -> basic.disable())
/// /
/// /                // Disable CSRF for development
/// /                .csrf(csrf -> csrf.disable())
/// /
/// /                // Authorization rules
/// /                .authorizeHttpRequests(auth -> auth
/// /                        .requestMatchers(
/// /                                "/", "/home", "/login", "/registration", "/register",
/// /                                "/css/**", "/js/**", "/images/**", "/static/**",
/// /                                "/pay", "/paymentSuccess", "/paymentFailed"
/// /                        ).permitAll()
/// /                        .anyRequest().permitAll()
/// /                )
/// /
/// /                // Logout config
/// /                .logout(logout -> logout
/// /                        .logoutUrl("/logout")
/// /                        .logoutSuccessUrl("/login?logout")
/// /                        .invalidateHttpSession(true)
/// /                        .clearAuthentication(true)
/// /                        .deleteCookies("JSESSIONID")
/// /                );
/// /
/// /        return http.build();
/// /    }
/// /
/// /
/// /    // ====== CORS CONFIGURATION =======
/// /
/// /    @Bean
/// /    public CorsConfigurationSource corsConfigurationSource() {
/// /        CorsConfiguration config = new CorsConfiguration();
/// /
/// /        // Localhost origin allowed
/// /        config.setAllowedOrigins(List.of(
/// /                "http://localhost:63342",    // JetBrains IDE server
/// /                "http://localhost:8080",     // Your Spring Boot backend
/// /                "http://127.0.0.1:63342",
/// /                "http://127.0.0.1:8080"
/// /        ));
/// /
/// /        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
/// /
/// /        // MUST include x-ijt here
/// /        config.setAllowedHeaders(Arrays.asList(
/// /                "Origin", "Content-Type", "Accept",
/// /                "X-Requested-With", "Authorization",
/// /                "x-ijt"  // <-- THIS FIXES YOUR ERROR
/// /        ));
/// /
/// /        config.setExposedHeaders(Arrays.asList("Content-Disposition"));
/// /        config.setAllowCredentials(true); // allow cookies if needed
/// /        config.setMaxAge(3600L);
/// /
/// /        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
/// /        source.registerCorsConfiguration("/**", config);
/// /
/// /        return source;
/// /    }
//}
