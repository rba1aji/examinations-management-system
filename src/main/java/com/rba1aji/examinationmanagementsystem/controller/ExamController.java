package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.model.Exam;
import com.rba1aji.examinationmanagementsystem.service.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamController {

  private final ExamService examService;

  @RequestMapping("/add-update-exam")
  public ResponseEntity<?> addUpdateExam(@Valid Exam exam) {
    return examService.saveUpdateExam(exam);
  }

  @GetMapping("/get-all-exam")
  public ResponseEntity<?> getAllExam(
      @RequestParam(name = "batch", required = false) String batch,
      @RequestParam(name = "semester", required = false, defaultValue = "0") int semester
  ) {
    return examService.getAllExam(batch, semester);
  }

}