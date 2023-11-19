package com.rba1aji.examinationmanagementsystem.security;

import com.rba1aji.examinationmanagementsystem.dto.JwtClaimsDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Set;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AllowedRolesAspect {


  @Around("@annotation(allowedRoles)")
  public Object verifyRolesAccess(ProceedingJoinPoint joinPoint, AllowedRoles allowedRoles) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    var claims = (JwtClaimsDto) request.getAttribute("claims");
    if (claims != null && Set.of(allowedRoles.value()).contains(claims.getRole())) {
      return joinPoint.proceed();
    } else {
      String msg = "403 Access denied. Only users with roles " + Arrays.stream(allowedRoles.value()).toList() + " can access this resource.";
      response.sendError(403, msg);
      return null;
    }
  }

}
