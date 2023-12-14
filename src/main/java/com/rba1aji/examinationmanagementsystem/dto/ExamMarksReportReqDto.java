package com.rba1aji.examinationmanagementsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExamMarksReportReqDto {
  private long examId;
  private List<Integer> examBatchIdList;
  private List<String> departmentCodeList;
  private List<Integer> courseIdList;
  private List<Integer> facultyIdList;
}
