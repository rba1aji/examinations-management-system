package com.rba1aji.examinationmanagementsystem.dto.request;

import lombok.Data;

@Data
public class MarksAddUpdateDto {
  private long id;
  private char attendance;
  private int marks;
  private long studentId;
  private long examId;
  private int courseId;
  private long examBatchId;
}
