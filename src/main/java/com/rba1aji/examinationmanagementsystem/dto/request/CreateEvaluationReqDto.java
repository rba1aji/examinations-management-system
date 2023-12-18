package com.rba1aji.examinationmanagementsystem.dto.request;

import lombok.Data;

@Data
public class CreateEvaluationReqDto {
  private int examId;
  private int courseId;
  private int facultyId;
  private long startPaperNumber;
  private long endPaperNumber;
  private int questionPaperConfigId;
}
