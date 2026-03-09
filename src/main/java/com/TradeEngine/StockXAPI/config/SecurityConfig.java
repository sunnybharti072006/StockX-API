package com.TradeEngine.StockXAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Dashboard aur static files ko open rakhein
                        .requestMatchers("/", "/index.html", "/static/**", "/ws-market/**").permitAll()
                        // Testing ke liye baaki APIs ko bhi allow karein
                        .anyRequest().permitAll()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}