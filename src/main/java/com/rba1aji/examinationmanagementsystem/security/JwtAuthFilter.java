package com.rba1aji.examinationmanagementsystem.security;

import com.rba1aji.examinationmanagementsystem.dto.JwtClaimsDto;
import com.rba1aji.examinationmanagementsystem.service.AuthService;
import com.rba1aji.examinationmanagementsystem.utilities.JwtAuthUtils;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
//      String token = AuthService.getAuthTokenFromCookies(request)
      String token = "";
      if (ValidationUtils.isNullOrEmpty(token)) {
        token = request.getHeader("Authorization");
        token = ValidationUtils.isNotNullAndNotEmpty(token) && token.contains("Bearer ") ? token.split("Bearer ")[1] : "";
      }
      if (ValidationUtils.isNullOrEmpty(token) || token.equals("undefined")) {
        filterChain.doFilter(request, response);
        return;
      }
      JwtClaimsDto claims = jwtAuthUtils.decodeToken(token);
      request.setAttribute("claims", claims);
      request.setAttribute("userId", claims.getUserId());
      request.setAttribute("userRole", claims.getRole());
      UserDetails user = new User(
          claims.getUsername(),
          token,
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
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Auth token expired!");
    } catch (JwtException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    } catch (AccessDeniedException e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    } catch (RuntimeException e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }
  }
}
