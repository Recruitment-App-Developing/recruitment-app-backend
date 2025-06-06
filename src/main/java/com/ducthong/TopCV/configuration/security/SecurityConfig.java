package com.ducthong.TopCV.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.ducthong.TopCV.constant.Endpoint;
import com.ducthong.TopCV.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AccountRepository accountRepository;
    private final JwtTokenFilter jwtTokenFilter;
    private static final String CATCH_ALL_WILDCARDS = "/**";
    public static final String[] PUBLIC_ENDPOINTS = {
        "/test" + CATCH_ALL_WILDCARDS,
        "/api-docs" + CATCH_ALL_WILDCARDS,
        "/swagger-ui" + CATCH_ALL_WILDCARDS,
        "/v3/api-docs" + CATCH_ALL_WILDCARDS,
        Endpoint.V1.HealthCheck.BASE + CATCH_ALL_WILDCARDS,
        Endpoint.V1.Authentication.BASE + CATCH_ALL_WILDCARDS,
        Endpoint.V1.Candidate.REGISTER_CANDIDATE,
        Endpoint.V1.Employer.REGISTER_EMPLOYER,
        Endpoint.V1.Admin.Auth.LOGIN,
        Endpoint.V1.Job.GET_LIST_JOB,
        Endpoint.V1.Job.SEARCH_JOB,
        Endpoint.V1.Job.GET_DETAIL,
        Endpoint.V1.Job.GET_DETAIL_JOB_PAGE,
        Endpoint.V1.Job.ADD_ONE,
        Endpoint.V1.Job.FIND_LIST_BY_COMPANY,
        Endpoint.V1.Company.GET_LIST,
        Endpoint.V1.Company.GET_LIST_FOR_EMPLOYER,
        Endpoint.V1.Company.GET_BRIEF_COMPANY,
        Endpoint.V1.Company.GET_DETAIL,
        Endpoint.V1.CvProfile.GET_ONE,
        Endpoint.V1.Statistic.STATISTIC_GENERAL_JOB_BY_INDUSTRY,
        Endpoint.V1.Statistic.STATISTIC_GENERAL_JOB_BY_DAY,
        Endpoint.V1.Address.GET_LIST_PROVINCE,
        Endpoint.V1.Address.GET_LIST_DISTRICT_BY_PROVINCE,
        Endpoint.V1.Address.GET_LIST_WARD_BY_DISTRICT
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> request.requestMatchers(
                        HttpMethod.POST, PUBLIC_ENDPOINTS)
                .permitAll()
                .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS)
                .permitAll()
                .requestMatchers(HttpMethod.GET, Endpoint.V1.Account.BASE)
                .hasAuthority("USER")
                .anyRequest()
                .authenticated());
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:3001");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
