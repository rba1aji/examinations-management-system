package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.constant.UserRole;
import com.rba1aji.examinationmanagementsystem.model.Marks;
import com.rba1aji.examinationmanagementsystem.security.AllowedRoles;
import com.rba1aji.examinationmanagementsystem.service.MarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/marks")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MarksController {

  private final MarksService marksService;

  @GetMapping("/v1/get-all-marks")
  @AllowedRoles({UserRole.FACULTY, UserRole.ADMIN})
  public ResponseEntity<?> getAllMarks(@RequestParam(name = "examBatch", required = false) String examBatchId,
                                       @RequestParam(name = "student", required = false) String studentId,
                                       @RequestParam(name = "exam", required = false) String examId,
                                       @RequestParam(name = "course", required = false) String courseId) {
    return marksService.getAllMarksByOptionalParams(examBatchId, studentId, examId, courseId);
  }

  @Deprecated
  @PostMapping("/v1/add-update-marks-list")
  @AllowedRoles({UserRole.FACULTY, UserRole.ADMIN})
  public ResponseEntity<?> addUpdateMarksList(@RequestBody List<Marks> marksList) {
    return marksService.addUpdateMarksList(marksList);
  }

  @PostMapping("/v2/add-update-marks-list/{examBatchId}")
  @AllowedRoles({UserRole.FACULTY, UserRole.ADMIN})
  public ResponseEntity<?> addUpdateMarksListForExamBatchId(@RequestBody List<Marks> marksList, @PathVariable("examBatchId") String examBatchId) {
    return marksService.addUpdateMarksListForExamBatch(marksList, examBatchId);
  }

  @GetMapping("/v1/get-all-marks-for-exam-batch")
  @AllowedRoles({UserRole.FACULTY, UserRole.ADMIN})
  public ResponseEntity<?> getMarksForExamBatch(@RequestParam(name = "examBatchId") String examBatchId) {
    return marksService.getMarksForExamBatch(examBatchId);
  }

}
