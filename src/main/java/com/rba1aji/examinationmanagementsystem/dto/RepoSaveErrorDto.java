package com.rba1aji.examinationmanagementsystem.dto;

import lombok.Data;

@Data
public class RepoSaveErrorDto {
  private String  id;
  private boolean status;
  private String  message;
}
