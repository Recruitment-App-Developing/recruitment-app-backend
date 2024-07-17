package com.ducthong.TopCV.configuration.security;

import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AccountRepository accountRepository;
    private final JwtTokenFilter jwtTokenFilter;
    private static final String CATCH_ALL_WILDCARDS = "/**";
    public static final String[] PUBLIC_ENDPOINTS = {
        "/swagger-ui"+CATCH_ALL_WILDCARDS,
        Endpoint.V1.HealthCheck.BASE+CATCH_ALL_WILDCARDS,
        Endpoint.V1.Authentication.BASE+CATCH_ALL_WILDCARDS,
        Endpoint.V1.Admin.Auth.LOGIN
    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(
                management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, Endpoint.V1.Account.BASE).hasAuthority("USER")
                        .anyRequest().authenticated());
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
