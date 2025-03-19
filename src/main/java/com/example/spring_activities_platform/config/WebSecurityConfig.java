package com.example.spring_activities_platform.config;

import com.example.spring_activities_platform.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/register","/registerUser").permitAll()
                        .requestMatchers("/profile").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers("/activities/get-activities","activities/feedback").hasAuthority("ROLE_USER")
                        .requestMatchers("/activities/rate/**","activities/comment/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/activities/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .permitAll()
                        .defaultSuccessUrl("/profile")
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
}
