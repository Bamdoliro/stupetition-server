package com.bamdoliro.stupetition.global.security;

import com.bamdoliro.stupetition.global.error.filter.GlobalErrorFilter;
import com.bamdoliro.stupetition.global.security.auth.AuthDetailsService;
import com.bamdoliro.stupetition.global.security.jwt.JwtTokenProvider;
import com.bamdoliro.stupetition.global.security.jwt.JwtValidateService;
import com.bamdoliro.stupetition.global.security.jwt.filter.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthDetailsService authDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtValidateService jwtValidateService;
    private final ObjectMapper mapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web ->
                web
                        .ignoring()
                        .mvcMatchers("/swagger-ui/**", "/configuration/**", "/swagger-resources/**", "/v3/api-docs", "/webjars/**", "/webjars/springfox-swagger-ui/*.{js,css}");
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .rememberMe().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/auth/google/**").permitAll()
                .antMatchers(HttpMethod.POST, "/school", "/user").hasRole("ADMIN")
                .antMatchers("/school/search").permitAll()
                .antMatchers("/answer/**").hasRole("STUDENT_COUNCIL")
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(
                                authDetailsService, jwtTokenProvider, jwtValidateService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new GlobalErrorFilter(mapper), JwtAuthenticationFilter.class);


        return http.build();
    }
}
