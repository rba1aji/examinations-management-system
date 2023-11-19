package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.constant.UserRole;
import com.rba1aji.examinationmanagementsystem.security.AllowedRoles;
import com.rba1aji.examinationmanagementsystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {

  private final StudentService studentService;

  @GetMapping("/get-all-students")
  @AllowedRoles({UserRole.ADMIN})
  public ResponseEntity<?> getAllStudents() {
    return studentService.getAllStudents();
  }

  @GetMapping("/get-profile")
  @AllowedRoles(UserRole.STUDENT)
  public ResponseEntity<?> getStudentProfile() {
    return studentService.getSessionStudent();
  }

}
