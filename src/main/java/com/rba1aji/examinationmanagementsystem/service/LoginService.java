package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.dto.JwtClaimsDto;
import com.rba1aji.examinationmanagementsystem.dto.request.LoginRequestDto;
import com.rba1aji.examinationmanagementsystem.model.Student;
import com.rba1aji.examinationmanagementsystem.repository.StudentRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.EncryptionUtils;
import com.rba1aji.examinationmanagementsystem.utilities.JwtAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LoginService {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private JwtAuthUtils jwtAuthUtils;

  @Autowired
  private BaseResponse baseResponse;

  public ResponseEntity<?> adminLogin(LoginRequestDto dto) {
    return null;
  }

  public ResponseEntity<?> facultyLogin(LoginRequestDto dto) {
    return null;
  }

  public ResponseEntity<?> studentLogin(LoginRequestDto dto) {
    try {
      long id = Long.parseLong(dto.getId());
      Optional<Student> student = studentRepository.findById(id);
      if (student.isEmpty()) {
        return baseResponse.errorResponse("Student not found with id: " + id, HttpStatus.NOT_FOUND);
      }
      if (!EncryptionUtils.verify(dto.getPassword(), student.get().getPassword())) {
        return baseResponse.errorResponse("Invalid password", HttpStatus.UNAUTHORIZED);
      }
      var claims = new JwtClaimsDto();
      claims.setRole("student");
      return baseResponse.successResponse(jwtAuthUtils.generateToken(claims));
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }
}
