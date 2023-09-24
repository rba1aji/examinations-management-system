package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.dto.request.DegreeSaveDto;
import com.rba1aji.examinationmanagementsystem.dto.request.DepartmentSaveDto;
import com.rba1aji.examinationmanagementsystem.model.Department;
import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.security.UserRegistrationService;
import com.rba1aji.examinationmanagementsystem.service.DegreeService;
import com.rba1aji.examinationmanagementsystem.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/registration/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationController {

  private final UserRegistrationService userRegistrationService;
  private final DepartmentService       departmentService;
  private final DegreeService           degreeService;

  @PostMapping("/register-faculty")
  public ResponseEntity<?> registerFaculty(@Valid @RequestBody Faculty faculty) {
    return userRegistrationService.registerFaculty(faculty);
  }

  @PostMapping("/excel-register-students")
  public ResponseEntity<?> excelRegisterStudents(@RequestPart("file") MultipartFile file) {
    return userRegistrationService.excelRegisterStudents(file);
  }

  @PostMapping("/excel-register-faculties")
  public ResponseEntity<?> excelRegisterFaculties(@RequestPart("file") MultipartFile file) {
    return userRegistrationService.excelRegisterFaculties(file);
  }

  @PostMapping("/save-update-department-list")
  public ResponseEntity<?> saveUpdateDepartmentList(@Valid @RequestBody List<DepartmentSaveDto> departmentList) {
    return departmentService.saveUpdateDepartmentList(departmentList);
  }

  @PostMapping("/save-update-degree-list")
  public ResponseEntity<?> saveUpdateDegreeList(@Valid @RequestBody List<DegreeSaveDto> departmentList) {
    return degreeService.saveUpdateDegreeList(departmentList);
  }

}
