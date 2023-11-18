package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faculty/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacultyController {

  private final FacultyService facultyService;

  @GetMapping("/get-all-faculties")
  public ResponseEntity<?> getAllFaculties() {
    return facultyService.getAllFaculties();
  }

  @GetMapping("/get-profile")
  public ResponseEntity<?> getFaculty() {
    return facultyService.getSessionFaculty();
  }

  @GetMapping("/get-active-exam-batches")
  public ResponseEntity<?> getActiveExamBatches() {
    return facultyService.getActiveExamBatches();
  }

}
