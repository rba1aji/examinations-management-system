package com.rba1aji.examinationmanagementsystem.security;

import com.rba1aji.examinationmanagementsystem.dto.JwtClaimsDto;
import com.rba1aji.examinationmanagementsystem.utilities.JwtAuthUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtAuthUtils jwtAuthUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
      if (authorization == null || !authorization.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }
      final String token = authorization.split(" ")[1].trim();
      JwtClaimsDto claims = jwtAuthUtils.decodeToken(token);
      request.setAttribute("claims", claims);
      UserDetails user = new User(
          claims.getUsername(),
          null,
          List.of(claims)
      );
      var authToken = new UsernamePasswordAuthenticationToken(
          user,
          null,
          user.getAuthorities()
      );
      SecurityContextHolder.getContext().setAuthentication(authToken);
      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }
  }
}
