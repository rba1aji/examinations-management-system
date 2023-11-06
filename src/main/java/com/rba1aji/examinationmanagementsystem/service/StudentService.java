package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.Student;
import com.rba1aji.examinationmanagementsystem.repository.StudentRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentService {

  private final StudentRepository studentRepository;
  private final BaseResponse baseResponse;

  public ResponseEntity<?> getAllStudents() {
    try {
      List<Student> studentList = studentRepository.findAll();
      return baseResponse.successResponse(studentList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
