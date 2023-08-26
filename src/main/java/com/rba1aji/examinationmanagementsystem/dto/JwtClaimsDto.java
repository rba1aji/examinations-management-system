package com.rba1aji.examinationmanagementsystem.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class JwtClaimsDto implements GrantedAuthority {
  private String role;

  @Override
  public String getAuthority() {
    return role;
  }
}
