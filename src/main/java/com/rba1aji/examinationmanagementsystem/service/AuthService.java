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

  public ResponseEntity<?> adminLogin(LoginRequestDto dto) {
    try {
      Optional<Admin> admin = adminRepository.findById(dto.getUsername());
      if (admin.isEmpty())
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Invalid username!");
      if (!EncryptionUtils.verify(dto.getPassword(), admin.get().getPassword()))
        return baseResponse.errorResponse(HttpStatus.UNAUTHORIZED, "Invalid password!");
      var claims = JwtClaimsDto.builder()
          .role(UserRoleConstant.ADMIN)
          .username(dto.getUsername())
          .build();
      String token = jwtAuthUtils.generateToken(claims);
      addAuthTokenInCookies(response, token);
      var data = Map.of("role", UserRoleConstant.ADMIN);
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
      if (!EncryptionUtils.verify(dto.getPassword(), faculty.get().getPassword()))
        return baseResponse.errorResponse(HttpStatus.UNAUTHORIZED, "Invalid password!");
      var claims = JwtClaimsDto.builder()
          .role(UserRoleConstant.FACULTY)
          .username(dto.getUsername())
          .build();
      String token = jwtAuthUtils.generateToken(claims);
      addAuthTokenInCookies(response, token);
      var data = Map.of("role", UserRoleConstant.FACULTY);
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
      if (!EncryptionUtils.verify(dto.getPassword(), student.get().getPassword()))
        return baseResponse.errorResponse(HttpStatus.UNAUTHORIZED, "Invalid password!");
      var claims = JwtClaimsDto.builder()
          .role(UserRoleConstant.STUDENT)
          .username(dto.getUsername())
          .build();
      String token = jwtAuthUtils.generateToken(claims);
      addAuthTokenInCookies(response, token);
      var data = Map.of("role", UserRoleConstant.STUDENT);
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

}
