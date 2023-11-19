package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.ExamBatch;
import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.repository.ExamBatchRepository;
import com.rba1aji.examinationmanagementsystem.repository.FacultyRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacultyService {

  private final HttpServletRequest request;
  private final FacultyRepository facultyRepository;
  private final BaseResponse baseResponse;
  private final ExamBatchRepository examBatchRepository;

  public ResponseEntity<?> getAllFaculties() {
    try {
      List<Faculty> facultyList = facultyRepository.findAll();
      return baseResponse.successResponse(facultyList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getSessionFaculty() {
    try {
      int id = (int) request.getAttribute("userId");
      return baseResponse.successResponse(
          facultyRepository.findById(id).orElse(null)
      );
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getActiveExamBatches() {
    try {
      int facultyId = CommonUtils.getInt(request.getAttribute("userId"));
      List<ExamBatch> examBatchList = examBatchRepository.findAllByFacultyIdAndActiveOrderByStartTimeDesc(facultyId, true);
      return baseResponse.successResponse(examBatchList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }
}
