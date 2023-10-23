package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.dto.request.AddUpdateExamReqDto;
import com.rba1aji.examinationmanagementsystem.model.Department;
import com.rba1aji.examinationmanagementsystem.model.Exam;
import com.rba1aji.examinationmanagementsystem.repository.ExamRepository;
import com.rba1aji.examinationmanagementsystem.repository.specification.ExamSpecification;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.CommonUtils;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamService {

  private final ExamRepository examRepository;
  private final BaseResponse baseResponse;
  private final ExamSpecification examSpecification;

  public ResponseEntity<?> saveUpdateExam(AddUpdateExamReqDto dto) {
    try {
      int semester = ValidationUtils.getInt(dto.getSemester());
      if (semester == 0) {
        return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid semester!");
      }
      Exam exam = Exam.builder()
          .id(ValidationUtils.getLong(dto.getId()))
          .name(dto.getName())
          .semester(semester)
          .batch(dto.getBatch())
          .departments(Arrays.stream(dto.getDepartments().split(",")).map(code -> Department.builder().code(code).build()).collect(Collectors.toSet()))
          .active(true)
          .build();
      exam = examRepository.saveAndFlush(exam);
      return baseResponse.successResponse(exam);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getAllExamByOptionalParams(String batch, String semester) {
    try {
      List<Exam> examList = examRepository.findAll(examSpecification.findAllByBatchAndSemesterOptional(
          CommonUtils.getStringList(batch),
          CommonUtils.getIntegerList(semester))
      );
      return baseResponse.successResponse(examList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getExamById(String examId) {
    try {
      Optional<Exam> exam = examRepository.findById(ValidationUtils.getLong(examId));
      if (exam.isPresent()) {
        return baseResponse.successResponse(exam.get());
      }
      return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Exam not found!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getDepartmentsForExam(String examId) {
    try {
      Optional<Exam> exam = examRepository.findById(ValidationUtils.getLong(examId));
      if (exam.isPresent()) {
        return baseResponse.successResponse(exam.get().getDepartments());
      }
      return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Exam not found!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
