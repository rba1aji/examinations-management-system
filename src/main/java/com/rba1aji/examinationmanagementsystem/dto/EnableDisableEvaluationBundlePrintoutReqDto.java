package com.rba1aji.examinationmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnableDisableEvaluationBundlePrintoutReqDto {
  private List<Integer> evaluationBundleId;
  private boolean disablePrintout;
}
