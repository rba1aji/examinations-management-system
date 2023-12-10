package com.rba1aji.examinationmanagementsystem.dto.request;

import lombok.Data;
import org.springframework.boot.configurationprocessor.json.JSONObject;

@Data
public class CreateEvaluationReqDto {
  private int examId;
  private int courseId;
  private int facultyId;
  private long startPaperNumber;
  private long endPaperNumber;
  private JSONObject configuration;
}
