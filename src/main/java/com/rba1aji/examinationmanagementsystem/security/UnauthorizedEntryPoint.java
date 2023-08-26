package com.rba1aji.examinationmanagementsystem.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
      throws IOException {
    response.sendError(
        HttpServletResponse.SC_UNAUTHORIZED,
        arg2.getMessage() != null ? arg2.getMessage() : "Access Denied"
    );
  }
}
