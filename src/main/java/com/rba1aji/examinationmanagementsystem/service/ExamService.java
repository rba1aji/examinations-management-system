package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.Exam;
import com.rba1aji.examinationmanagementsystem.repository.ExamRepository;
import com.rba1aji.examinationmanagementsystem.repository.specification.ExamSpecifications;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamService {

  private final ExamRepository examRepository;
  private final BaseResponse baseResponse;
  private final ExamSpecifications examSpecifications;

  public ResponseEntity<?> saveUpdateExam(Exam exam) {
    try {
      exam.setActive(true);
      exam = examRepository.saveAndFlush(exam);
      return baseResponse.successResponse(exam);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getAllExam(String batch, int semester) {
    try {
      List<Exam> examList = examRepository.findAll(examSpecifications.findAllByBatchAndSemesterOptional(batch, semester));
      return baseResponse.successResponse(examList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getExamById(String id) {
    try {
      Optional<Exam> exam = examRepository.findById(ValidationUtils.getLong(id));
      if (exam.isPresent()) {
        return baseResponse.successResponse(exam.get());
      }
      return baseResponse.errorResponse("Exam not found!", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
