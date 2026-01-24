package com.sdlccorp.hr.config;

import com.sdlccorp.hr.security.JwtAuthFilter;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        // IMPORTANT: enable CORS at the Spring Security layer
        // (reads CorsConfigurationSource bean from CorsConfig)
        http.cors(Customizer.withDefaults());

        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                // allow browser preflight without auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // public routes
                .requestMatchers("/", "/error").permitAll()
                .requestMatchers("/api/auth/**").permitAll()

                // Swagger / OpenAPI (optional, but handy for testing)
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // everything else requires JWT
                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}