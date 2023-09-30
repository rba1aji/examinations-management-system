package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.dto.request.DepartmentSaveDto;
import com.rba1aji.examinationmanagementsystem.model.Degree;
import com.rba1aji.examinationmanagementsystem.model.Department;
import com.rba1aji.examinationmanagementsystem.repository.DepartmentRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class DepartmentService {

  private final BaseResponse baseResponse;
  private final DepartmentRepository departmentRepository;

  public ResponseEntity<?> saveUpdateDepartmentList(List<DepartmentSaveDto> dtoList) {
    try {
      List<Department> departmentList = dtoList.stream().map(dto ->
          Department.builder()
              .code(dto.getCode())
              .name(dto.getName())
              .degree(Degree.builder().code(dto.getDegreeCode()).build())
              .build()
      ).toList();
      departmentList = departmentRepository.saveAllAndFlush(departmentList);
      return baseResponse.successResponse(departmentList);
    } catch (Exception e) {
      log.error("Error in saveUpdateDepartmentList(): ", e);
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getAllDepartment() {
    try {
      return baseResponse.successResponse(departmentRepository.findAll());
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getDepartmentByCode(String departmentCode) {
    try {
      Optional<Department> department = departmentRepository.findByCode(departmentCode);
      if (department.isPresent()) {
        return baseResponse.successResponse(department.get());
      }
      return baseResponse.errorResponse("Department not found!", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
