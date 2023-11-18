package com.rba1aji.examinationmanagementsystem.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
public class JwtClaimsDto implements GrantedAuthority {
  private Integer userId;
  private String role;
  private String username;

  @Override
  public String getAuthority() {
    return role;
  }
}
