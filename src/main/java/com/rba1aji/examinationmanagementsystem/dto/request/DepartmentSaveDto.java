package com.rba1aji.examinationmanagementsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartmentSaveDto {
  @NotBlank
  private String code;
  @NotBlank
  private String name;
  @NotBlank
  private String degreeCode;
}
