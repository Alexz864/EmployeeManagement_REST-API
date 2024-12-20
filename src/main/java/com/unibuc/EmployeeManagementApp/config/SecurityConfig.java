package com.unibuc.EmployeeManagementApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

//Configure Spring Security Bean
@Configuration
@SuppressWarnings("unused")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable) //Disable CSRF(Cross-Site Request Forgery)
                .authorizeHttpRequests(auth->auth
                        .anyRequest().permitAll()  //Allow unauthenticated access
                );

        return http.build();
    }

}
