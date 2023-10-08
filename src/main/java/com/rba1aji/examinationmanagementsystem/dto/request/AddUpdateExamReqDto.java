package com.rba1aji.examinationmanagementsystem.dto.request;

import lombok.Data;

@Data
public class AddUpdateExamReqDto {
  private String id;
  private String name;
  private String semester;
  private String batch;
  private String departments;
}
