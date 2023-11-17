package com.rba1aji.examinationmanagementsystem.dto.request;

import lombok.Data;

@Data
public class SaveUpdateCourseDto {

  private String code;

  private String acronym;

  private String name;

  private int credit;

  private int semester;

  private String department;

  private String batch;
}
