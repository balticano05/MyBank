package com.pet.bank.config.security;

import com.pet.bank.exception.type.SecurityConfigurationException;
import com.pet.bank.security.filter.JwtAuthFilter;
import com.pet.bank.security.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter authFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

        try {
            httpSecurity.csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(authFilter -> {
                        authFilter.requestMatchers(
                                "/api/v1/auth/authenticate",
                                "/api/v1/register"
                        ).permitAll();
                    }).authorizeHttpRequests(authFilter -> {
                        authFilter.requestMatchers("/api/v1/users/**").authenticated();
                    })
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                    .authenticationProvider(authenticationProvider());

            return httpSecurity.build();
        } catch (Exception e) {
            throw new SecurityConfigurationException("Failed to configure security: " + e.getMessage());
        }
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        try {
            return config.getAuthenticationManager();
        } catch (Exception e) {
            throw new SecurityConfigurationException("Failed to configure authentication manager: " + e.getMessage());
        }
    }

}