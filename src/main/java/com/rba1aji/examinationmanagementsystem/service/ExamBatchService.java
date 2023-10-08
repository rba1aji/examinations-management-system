package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.dto.request.AddUpdateExamBatchReqDto;
import com.rba1aji.examinationmanagementsystem.model.Course;
import com.rba1aji.examinationmanagementsystem.model.Exam;
import com.rba1aji.examinationmanagementsystem.model.ExamBatch;
import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.repository.ExamBatchRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamBatchService {

  private final BaseResponse baseResponse;
  private final ExamBatchRepository examBatchRepository;

  public ResponseEntity<?> saveUpdateExamBatch(AddUpdateExamBatchReqDto dto) {
    try {
      if (!ValidationUtils.validStartTimeEndTime(dto.getStartTime(), dto.getEndTime())) {
        return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Start Time, End Time!");
      }
      ExamBatch examBatch = ExamBatch.builder()
          .id(ValidationUtils.getLong(dto.getId()))
          .name(dto.getName())
          .students(dto.getStudents())
          .exam(Exam.builder().id(ValidationUtils.getLong(dto.getExamId())).build())
          .course(Course.builder().id(ValidationUtils.getInt(dto.getCourseId())).build())
          .faculty(Faculty.builder().id(ValidationUtils.getLong(dto.getFacultyId())).build())
          .startTime(dto.getStartTime())
          .endTime(dto.getEndTime())
          .venue(dto.getVenue())
          .build();
      return baseResponse.successResponse(examBatchRepository.saveAndFlush(examBatch));
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }
}
