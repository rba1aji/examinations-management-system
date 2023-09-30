package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DepartmentController {

  private final DepartmentService departmentService;

  @GetMapping("/get-all-department")
  public ResponseEntity<?> getAllDepartment() {
    return departmentService.getAllDepartment();
  }

  @GetMapping("/get-department")
  public ResponseEntity<?> getDepartment(@RequestParam(name = "code") String departmentCode) {
    return departmentService.getDepartmentByCode(departmentCode);
  }

}
