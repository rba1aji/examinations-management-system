package com.rba1aji.examinationmanagementsystem.config;

import com.rba1aji.examinationmanagementsystem.security.AccessDeniedEntryPoint;
import com.rba1aji.examinationmanagementsystem.security.JwtAuthFilter;
import com.rba1aji.examinationmanagementsystem.security.CrossOriginFilter;
import com.rba1aji.examinationmanagementsystem.security.AuthenticationEntryPoint;
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
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final AccessDeniedEntryPoint accessDeniedEntryPoint;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
      .cors(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize -> {
        authorize
          .requestMatchers("/auth/**").permitAll()
          .requestMatchers("/registration/**").authenticated()
          .requestMatchers("/faculty/**").authenticated()
          .requestMatchers("/student/**").authenticated()
          .requestMatchers("/department/**").authenticated()
          .requestMatchers("/course/**").authenticated()
          .requestMatchers("/exam-batch/**").authenticated()
          .requestMatchers("/exam/**").authenticated()
          .requestMatchers("/marks/**").authenticated()
          .requestMatchers("/report/**").authenticated()
          .requestMatchers("/evaluation/**").authenticated()
          .requestMatchers("/evaluation-bundle/**").authenticated()
          .requestMatchers("/evaluation-paper/**").authenticated()
          .anyRequest().permitAll();
      }).exceptionHandling(ex -> ex
        .accessDeniedHandler(accessDeniedEntryPoint)
        .authenticationEntryPoint(authenticationEntryPoint)
      );
    http.addFilterBefore(crossOriginFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}