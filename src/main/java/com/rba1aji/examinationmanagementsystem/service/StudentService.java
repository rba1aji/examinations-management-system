package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.BatchStudent;
import com.rba1aji.examinationmanagementsystem.model.Student;
import com.rba1aji.examinationmanagementsystem.repository.StudentRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentService {

  private final StudentRepository studentRepository;
  private final BaseResponse baseResponse;
  private final HttpServletRequest request;

  public ResponseEntity<?> getAllStudents() {
    try {
      List<Student> studentList = studentRepository.findAll();
      return baseResponse.successResponse(studentList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public List<Student> getStudentsForRegisterNumbersRange(Set<BatchStudent> batchStudents) {
    List<Student> students = new ArrayList<>();
    if (batchStudents != null) {
      batchStudents.forEach(batch -> {
            if (ValidationUtils.areNotNullAndNotEmpty(batch.getStartRegNum(), batch.getEndRegNum()))
              students.addAll(
                  studentRepository.findAllByRegisterNumberBetween(batch.getStartRegNum(), batch.getEndRegNum())
              );
          }
      );
    }
    return students;
  }

  public ResponseEntity<?> getSessionStudent() {
    try {
      int id = (int) request.getAttribute("userId");
      return baseResponse.successResponse(
          studentRepository.findById(id).orElse(null)
      );
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }
}
