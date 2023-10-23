package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.dto.request.MarksAddUpdateDto;
import com.rba1aji.examinationmanagementsystem.service.MarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/marks/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MarksController {

  private final MarksService marksService;

  @GetMapping("/get-all-marks")
  public ResponseEntity<?> getAllMarks(@RequestParam(name = "examBatch", required = false) String examBatchId,
                                       @RequestParam(name = "student", required = false) String studentId,
                                       @RequestParam(name = "exam", required = false) String examId,
                                       @RequestParam(name = "course", required = false) String courseId) {
    return marksService.getAllMarksByOptionalParams(examBatchId, studentId, examId, courseId);
  }

  @PostMapping("/add-update-marks-list")
  private ResponseEntity<?> addUpdateMarksList(@RequestBody List<MarksAddUpdateDto> marksList) {
    return marksService.addUpdateMarksList(marksList);
  }

}
