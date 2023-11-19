package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.constant.UserRole;
import com.rba1aji.examinationmanagementsystem.dto.request.AddUpdateExamReqDto;
import com.rba1aji.examinationmanagementsystem.security.AllowedRoles;
import com.rba1aji.examinationmanagementsystem.service.ExamService;
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
@RequestMapping("/exam/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamController {

  private final ExamService examService;

  @PostMapping("/add-update-exam")
  @AllowedRoles(UserRole.ADMIN)
  public ResponseEntity<?> addUpdateExam(@Valid @RequestBody AddUpdateExamReqDto dto) {
    return examService.saveUpdateExam(dto);
  }

  @GetMapping("/get-all-exam")
  @AllowedRoles(UserRole.ADMIN)
  public ResponseEntity<?> getAllExam(
      @RequestParam(name = "batch") String batch,
      @RequestParam(name = "semester", required = false) String semester
  ) {
    return examService.getAllExamByOptionalParams(batch, semester);
  }

  @GetMapping("/get-exam")
  @AllowedRoles({UserRole.ADMIN, UserRole.FACULTY})
  public ResponseEntity<?> getExam(@RequestParam(name = "examId") String id) {
    return examService.getExamById(id);
  }

  @GetMapping("/get-departments-for-exam")
  @AllowedRoles({UserRole.ADMIN, UserRole.FACULTY})
  public ResponseEntity<?> getDepartmentsForExam(@RequestParam("examId") String examId) {
    return examService.getDepartmentsForExam(examId);
  }

}