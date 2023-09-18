package com.rba1aji.examinationmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DegreeSaveDto {
  @NotBlank
  private String code;
  @NotBlank
  private String name;
}
