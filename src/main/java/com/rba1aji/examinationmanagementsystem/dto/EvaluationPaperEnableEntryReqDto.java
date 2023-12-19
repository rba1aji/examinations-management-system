package com.rba1aji.examinationmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class EvaluationPaperEnableEntryReqDto {
  private int evaluationId;
  private List<Long> evaluationPaperNumbers;
}
