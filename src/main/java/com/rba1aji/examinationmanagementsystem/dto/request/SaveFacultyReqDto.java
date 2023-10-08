package com.rba1aji.examinationmanagementsystem.dto.request;

import lombok.Data;

@Data
public class SaveFacultyReqDto {
  private String userName;
  private String password;
  private String fullName;
  private String designation;
  private String phone;
  private String email;
  private String departmentCode;
}
