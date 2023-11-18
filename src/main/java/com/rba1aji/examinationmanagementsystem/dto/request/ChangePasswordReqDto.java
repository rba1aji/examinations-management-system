package com.rba1aji.examinationmanagementsystem.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordReqDto {
  @NotNull
  @NotEmpty
  private String currentPassword;
  @NotNull
  @NotEmpty
  private String newPassword;
}
