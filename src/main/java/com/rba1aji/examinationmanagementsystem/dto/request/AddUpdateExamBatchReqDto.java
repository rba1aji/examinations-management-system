package com.rba1aji.examinationmanagementsystem.dto.request;

import com.rba1aji.examinationmanagementsystem.model.BatchStudent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
public class AddUpdateExamBatchReqDto {

  private String id;

  @NotBlank
  private String name;

  @NotNull
  @NotEmpty
  private Set<BatchStudent> students;

  private String examId;

  private String courseId;

  private String facultyId;

  @NotNull
  private Timestamp startTime;

  @NotNull
  private Timestamp endTime;

  private boolean enableMarksEntryByAdmin;

  private String venue;
}
