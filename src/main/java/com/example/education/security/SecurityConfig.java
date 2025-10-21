package com.example.education.security;


import com.example.education.exception.JWTAccessDeniedHandler;
import com.example.education.exception.JWTAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JWTAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v1/auth/**",
            "/v1/user/**",
            "/V1/**"
    };
    private static final String[] USER_WHITELIST = {
            "/v1/course/**"
    };
    private static final String[] ADMIN_WHITELIST = {
            "/v1/material/**",
            "/v1/question/**",
            "/v1/admin/**",
            "/v1/interview/**",
            "/v1/admin/***",
            "/api/s3/**"
    };
    private static final String[] COURSE_ADMIN_WHITELIST = {
            "/v1/admin-course/**",

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .requestMatchers(SUPER_ADMIN_WHITELIST).hasAnyRole("SUPER_ADMIN")
                        .requestMatchers(USER_WHITELIST).permitAll()
//                        hasAnyRole("USER")
                        .requestMatchers(ADMIN_WHITELIST).permitAll()
//                        .hasAnyRole("ADMIN")
                        .requestMatchers(COURSE_ADMIN_WHITELIST).permitAll()

                        .anyRequest().authenticated())


                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .build();
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
