package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.security.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/registration/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

  private final RegistrationService registrationService;

  @PostMapping("/register-faculty")
  public ResponseEntity<?> registerFaculty(@Valid @RequestBody Faculty faculty) {
    return registrationService.registerFaculty(faculty);
  }

  @PostMapping("/bulk-register-students")
  public ResponseEntity<?> bulkRegisterStudents(@RequestPart("file") MultipartFile file) {
    return null;
  }

  @PostMapping("/bulk-register-faculties")
  public ResponseEntity<?> bulkRegisterFaculties(@RequestPart("file") MultipartFile file) {
    return null;
  }

}
