package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.constant.UserRole;
import com.rba1aji.examinationmanagementsystem.dto.request.AddUpdateExamBatchReqDto;
import com.rba1aji.examinationmanagementsystem.security.AllowedRoles;
import com.rba1aji.examinationmanagementsystem.service.ExamBatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam-batch/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamBatchController {

  private final ExamBatchService examBatchService;

  @PostMapping("/add-update-exam-batch")
  @AllowedRoles(UserRole.ADMIN)
  public ResponseEntity<?> addUpdateExamBatch(@Valid @RequestBody AddUpdateExamBatchReqDto dto) {
    return examBatchService.saveUpdateExamBatch(dto);
  }

  @GetMapping("/get-all-exam-batch")
  @AllowedRoles(UserRole.ADMIN)
  public ResponseEntity<?> getAllExamBatch(@RequestParam(name = "faculty", required = false) String facultyId,
                                           @RequestParam(name = "exam", required = false) String examId,
                                           @RequestParam(name = "course", required = false) String courseId) {
    return examBatchService.getAllExamBatchByOptionalParams(facultyId, examId, courseId);
  }

  @GetMapping("/get-exam-batch")
  @AllowedRoles({UserRole.ADMIN, UserRole.FACULTY, UserRole.STUDENT})
  public ResponseEntity<?> getExamBatch(@RequestParam("examBatchId") String examBatchId) {
    return examBatchService.getExamBatchById(examBatchId);
  }

}
