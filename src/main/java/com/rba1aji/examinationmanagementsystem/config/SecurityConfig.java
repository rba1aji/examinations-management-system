package com.rba1aji.examinationmanagementsystem.config;

import com.rba1aji.examinationmanagementsystem.security.JwtAuthFilter;
import com.rba1aji.examinationmanagementsystem.security.CrossOriginFilter;
import com.rba1aji.examinationmanagementsystem.security.UnauthorizedEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;

  private final CrossOriginFilter crossOriginFilter;

  private final UnauthorizedEntryPoint unauthorizedEntryPoint;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorize -> {
          authorize
              .requestMatchers("/login/**").permitAll()
              .anyRequest().permitAll();
        })
        .exceptionHandling(
            ex -> ex.authenticationEntryPoint(unauthorizedEntryPoint)
        );
    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(crossOriginFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

}