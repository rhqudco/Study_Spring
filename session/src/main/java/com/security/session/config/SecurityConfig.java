package com.security.session.config;

import com.security.session.session.SessionAccessDeniedHandler;
import com.security.session.session.SessionAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsFilter corsFilter;
  private final SessionAccessDeniedHandler sessionAccessDeniedHandler;
  private final SessionAuthenticationEntryPoint sessionAuthenticationEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
            .antMatchers("/resources/**", "/css/**", "/vendor/**",
                    "/js/**", "/favicon/**", "/img/**");
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().disable()
            .csrf().disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                    .accessDeniedHandler(sessionAccessDeniedHandler)
                    .authenticationEntryPoint(sessionAuthenticationEntryPoint)
            )
            .sessionManagement(sessionManagement -> sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionFixation().changeSessionId()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
            )
            .authorizeRequests()
            .antMatchers("/v1/member/join", "/v1/member/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().disable()
            .httpBasic().disable();

    return http.build();
  }
}
