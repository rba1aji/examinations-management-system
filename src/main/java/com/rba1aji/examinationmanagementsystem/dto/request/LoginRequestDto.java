package com.rba1aji.examinationmanagementsystem.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {
  @NotNull
  @NotEmpty
  private String username;
  @NotNull
  @NotEmpty
  private String password;
}
