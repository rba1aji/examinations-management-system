package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.dto.request.AddUpdateExamBatchReqDto;
import com.rba1aji.examinationmanagementsystem.service.ExamBatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam-batch/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamBatchController {

  private final ExamBatchService examBatchService;

  @PostMapping("/add-update-exam-batch")
  public ResponseEntity<?> addUpdateExamBatch(@Valid @RequestBody AddUpdateExamBatchReqDto dto) {
    return examBatchService.saveUpdateExamBatch(dto);
  }

}
