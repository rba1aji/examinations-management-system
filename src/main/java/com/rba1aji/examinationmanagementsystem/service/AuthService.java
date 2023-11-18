package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.constant.UserRoleConstant;
import com.rba1aji.examinationmanagementsystem.dto.JwtClaimsDto;
import com.rba1aji.examinationmanagementsystem.dto.request.ChangePasswordReqDto;
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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

  private final AdminRepository adminRepository;
  private final FacultyRepository facultyRepository;
  private final StudentRepository studentRepository;
  private final JwtAuthUtils jwtAuthUtils;
  private final BaseResponse baseResponse;
  private final HttpServletResponse response;
  private final HttpServletRequest request;

  public ResponseEntity<?> adminLogin(LoginRequestDto dto) {
    try {
      Optional<Admin> admin = adminRepository.findByUsername(dto.getUsername());
      if (admin.isEmpty())
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Invalid username!");
      if (!EncryptionUtils.verifyPlainForHashed(dto.getPassword(), admin.get().getPassword()))
        return baseResponse.errorResponse(HttpStatus.UNAUTHORIZED, "Invalid password!");
      var claims = JwtClaimsDto.builder()
          .role(UserRoleConstant.ADMIN)
          .userId(admin.get().getId())
          .username(dto.getUsername())
          .build();
      String token = jwtAuthUtils.generateToken(claims);
      addAuthTokenInCookies(response, token);
      var data = Map.of("role", UserRoleConstant.ADMIN,
          "token", token);
      return baseResponse.successResponse(data, "Login successful!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> facultyLogin(LoginRequestDto dto) {
    try {
      Optional<Faculty> faculty = facultyRepository.findByUsername(dto.getUsername());
      if (faculty.isEmpty())
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Invalid username!");
      if (!EncryptionUtils.verifyPlainForHashed(dto.getPassword(), faculty.get().getPassword()))
        return baseResponse.errorResponse(HttpStatus.UNAUTHORIZED, "Invalid password!");
      var claims = JwtClaimsDto.builder()
          .role(UserRoleConstant.FACULTY)
          .userId(faculty.get().getId())
          .username(dto.getUsername())
          .build();
      String token = jwtAuthUtils.generateToken(claims);
      addAuthTokenInCookies(response, token);
      var data = Map.of("role", UserRoleConstant.FACULTY,
          "token", token);
      return baseResponse.successResponse(data, "Login successful!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> studentLogin(LoginRequestDto dto) {
    try {
      Optional<Student> student = studentRepository.findByRegisterNumber(dto.getUsername());
      if (student.isEmpty())
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Invalid username!");
      if (!EncryptionUtils.verifyPlainForHashed(dto.getPassword(), student.get().getPassword()))
        return baseResponse.errorResponse(HttpStatus.UNAUTHORIZED, "Invalid password!");
      var claims = JwtClaimsDto.builder()
          .role(UserRoleConstant.STUDENT)
          .userId(student.get().getId())
          .username(dto.getUsername())
          .build();
      String token = jwtAuthUtils.generateToken(claims);
      addAuthTokenInCookies(response, token);
      var data = Map.of("role", UserRoleConstant.STUDENT,
          "token", token);
      return baseResponse.successResponse(data, "Login successful!");
    } catch (Exception e) {
      e.printStackTrace();
      return baseResponse.errorResponse(e);
    }
  }

  private static void addAuthTokenInCookies(HttpServletResponse response, String token) {
    Cookie cookie = new Cookie("authtoken", token);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60 * 12);                   // expiry - 12 hours
    cookie.setSecure(true);
    response.addCookie(cookie);
  }

  public ResponseEntity<?> userLogout() {
    try {
      Cookie cookie = new Cookie("authtoken", null);
      cookie.setPath("/");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
      return baseResponse.successResponse(true, "Logout successful!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public static String getAuthTokenFromCookies(HttpServletRequest request) {
    try {
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if (cookie.getName().equals("authtoken")) {
            return cookie.getValue();
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public ResponseEntity<?> changeUserPassword(ChangePasswordReqDto dto) {
    var claims = (JwtClaimsDto) request.getAttribute("claims");
//    User user = switch (claims.getRole()) {
//      case UserRoleConstant.ADMIN -> adminRepository.findById(claims.getUserId()).orElse(null);
//      case UserRoleConstant.FACULTY -> facultyRepository.findById(claims.getUserId()).orElse(null);
//      case UserRoleConstant.STUDENT -> studentRepository.findById(claims.getUserId()).orElse(null);
//      default -> null;
//    };
    return switch (claims.getRole()) {
      case UserRoleConstant.ADMIN -> changeAdminPassword(claims.getUserId(), dto);
      case UserRoleConstant.FACULTY -> changeFacultyPassword(claims.getUserId(), dto);
      case UserRoleConstant.STUDENT -> changeStudentPassword(claims.getUserId(), dto);
      default -> baseResponse.errorResponse(HttpStatus.NOT_FOUND, "User role not found in token claims!");
    };
  }


  private ResponseEntity<?> changeStudentPassword(int userId, ChangePasswordReqDto dto) {
    Optional<Student> studentOp = studentRepository.findById(userId);
    if (studentOp.isPresent()) {
      Student student = studentOp.get();
      if (EncryptionUtils.verifyPlainForHashed(dto.getCurrentPassword(), student.getPassword())) {
        student.setPassword(EncryptionUtils.encrypt(dto.getNewPassword()));
        studentRepository.saveAndFlush(student);
        return baseResponse.successResponse("Password changed successfully!");
      } else {
        return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid current password!");
      }
    } else {
      return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Student user not found!");
    }
  }

  private ResponseEntity<?> changeFacultyPassword(int userId, ChangePasswordReqDto dto) {
    Optional<Faculty> facultyOp = facultyRepository.findById(userId);
    if (facultyOp.isPresent()) {
      Faculty faculty = facultyOp.get();
      if (EncryptionUtils.verifyPlainForHashed(dto.getCurrentPassword(), faculty.getPassword())) {
        faculty.setPassword(EncryptionUtils.encrypt(dto.getNewPassword()));
        facultyRepository.saveAndFlush(faculty);
        return baseResponse.successResponse("Password changed successfully!");
      } else {
        return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid current password!");
      }
    } else {
      return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Faculty user not found!");
    }
  }

  private ResponseEntity<?> changeAdminPassword(int userId, ChangePasswordReqDto dto) {
    Optional<Admin> adminOp = adminRepository.findById(userId);
    if (adminOp.isPresent()) {
      Admin admin = adminOp.get();
      if (EncryptionUtils.verifyPlainForHashed(dto.getCurrentPassword(), admin.getPassword())) {
        admin.setPassword(EncryptionUtils.encrypt(dto.getNewPassword()));
        adminRepository.saveAndFlush(admin);
        return baseResponse.successResponse("Password changed successfully!");
      } else {
        return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid current password!");
      }
    } else {
      return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Admin user not found!");
    }
  }
}
