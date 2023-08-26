package com.rba1aji.examinationmanagementsystem.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class CrossOriginFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    var httpRequest = (HttpServletRequest) request;
    var httpResponse = (HttpServletResponse) response;
    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
    httpResponse.setHeader("Access-Control-Allow-Headers", "*");
    httpResponse.setHeader("Access-Control-Max-Age", "3600");
    chain.doFilter(request, httpResponse);
  }
}

