package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.constant.UserRoleConstant;
import com.rba1aji.examinationmanagementsystem.dto.JwtClaimsDto;
import com.rba1aji.examinationmanagementsystem.dto.request.LoginRequestDto;
import com.rba1aji.examinationmanagementsystem.model.Admin;
import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.model.Student;
import com.rba1aji.examinationmanagementsystem.repository.AdminRepository;
import com.rba1aji.examinationmanagementsystem.repository.FacultyRepository;
import com.rba1aji.examinationmanagementsystem.repository.StudentRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.EncryptionUtils;
import com.rba1aji.examinationmanagementsystem.utilities.JwtAuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginService {

  private final AdminRepository   adminRepository;
  private final FacultyRepository facultyRepository;
  private final StudentRepository studentRepository;
  private final JwtAuthUtils      jwtAuthUtils;
  private final BaseResponse      baseResponse;

  public ResponseEntity<?> adminLogin(LoginRequestDto dto) {
    try {
      Optional<Admin> admin = adminRepository.findById(dto.getUsername());
      if (admin.isEmpty())
        return baseResponse.errorResponse("Invalid username!", HttpStatus.NOT_FOUND);
      if (!EncryptionUtils.verify(dto.getPassword(), admin.get().getPassword()))
        return baseResponse.errorResponse("Invalid password!", HttpStatus.UNAUTHORIZED);
      var claims = JwtClaimsDto.builder()
          .role(UserRoleConstant.ADMIN)
          .username(dto.getUsername())
          .password(dto.getPassword())
          .build();
      return baseResponse.successResponse(
          Map.of(
              "token", jwtAuthUtils.generateToken(claims)
          )
      );
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> facultyLogin(LoginRequestDto dto) {
    try {
      Optional<Faculty> faculty = facultyRepository.findByUsername(dto.getUsername());
      if (faculty.isEmpty())
        return baseResponse.errorResponse("Invalid username!", HttpStatus.NOT_FOUND);
      if (!EncryptionUtils.verify(dto.getPassword(), faculty.get().getPassword()))
        return baseResponse.errorResponse("Invalid password!", HttpStatus.UNAUTHORIZED);
      var claims = JwtClaimsDto.builder()
          .role(UserRoleConstant.FACULTY)
          .username(dto.getUsername())
          .password(dto.getPassword())
          .build();
      return baseResponse.successResponse(
          Map.of(
              "token", jwtAuthUtils.generateToken(claims)
          )
      );
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> studentLogin(LoginRequestDto dto) {
    try {
      Optional<Student> student = studentRepository.findByRegisterNumber(dto.getUsername());
      if (student.isEmpty())
        return baseResponse.errorResponse("Invalid username!", HttpStatus.NOT_FOUND);
      if (!EncryptionUtils.verify(dto.getPassword(), student.get().getPassword()))
        return baseResponse.errorResponse("Invalid password!", HttpStatus.UNAUTHORIZED);
      var claims = JwtClaimsDto.builder()
          .role(UserRoleConstant.STUDENT)
          .username(dto.getUsername())
          .password(dto.getPassword())
          .build();
      return baseResponse.successResponse(
          Map.of(
              "token", jwtAuthUtils.generateToken(claims)
          )
      );
    } catch (Exception e) {
      e.printStackTrace();
      return baseResponse.errorResponse(e);
    }
  }
}
