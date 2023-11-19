package com.rba1aji.examinationmanagementsystem.dto.request;

import lombok.Data;

@Data
public class AddUpdateExamReqDto {
  private int id;
  private String name;
  private int semester;
  private String batch;
  private String departments;
}
