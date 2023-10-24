package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.dto.request.LoginRequestDto;
import com.rba1aji.examinationmanagementsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/admin-login")
  public ResponseEntity<?> adminLogin(@RequestBody LoginRequestDto dto) {
    return authService.adminLogin(dto);
  }

  @PostMapping("/faculty-login")
  public ResponseEntity<?> facultyLogin(@RequestBody LoginRequestDto dto) {
    return authService.facultyLogin(dto);
  }

  @PostMapping("/student-login")
  public ResponseEntity<?> studentLogin(@RequestBody LoginRequestDto dto) {
    return authService.studentLogin(dto);
  }

  @PostMapping("/logout")
  public ResponseEntity<?> userLogout() {
    return authService.userLogout();
  }

}
