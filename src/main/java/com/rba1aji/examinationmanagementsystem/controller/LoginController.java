package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.dto.request.LoginRequestDto;
import com.rba1aji.examinationmanagementsystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login/v1")
public class LoginController {

  @Autowired
  private LoginService loginService;

  @PostMapping("/admin-login")
  public ResponseEntity<?> adminLogin(@RequestBody LoginRequestDto dto) {
    return loginService.adminLogin(dto);
  }

  @PostMapping("/faculty-login")
  public ResponseEntity<?> facultyLogin(@RequestBody LoginRequestDto dto){
    return loginService.facultyLogin(dto);
  }

  @PostMapping("/student-login")
  public ResponseEntity<?> studentLogin(@RequestBody LoginRequestDto dto){
    return loginService.studentLogin(dto);
  }

}
