package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.Marks;
import com.rba1aji.examinationmanagementsystem.repository.MarksRepository;
import com.rba1aji.examinationmanagementsystem.repository.specification.MarksSpecification;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MarksService {

  private final MarksRepository marksRepository;
  private final BaseResponse baseResponse;
  private final MarksSpecification marksSpecification;

  public ResponseEntity<?> getAllMarksByOptionalParams(String examBatchId, String studentId, String examId, String courseId) {
    try {
      Specification<Marks> marksSpec = marksSpecification.getAllByExamBatchIdAndStudentIdAndExamIdAndCourseIdOptional(
          ValidationUtils.getLong(examBatchId),
          ValidationUtils.getLong(studentId),
          ValidationUtils.getLong(examId),
          ValidationUtils.getInt(courseId)
      );
      List<Marks> marks;
      if (marksSpec != null) {
        marks = marksRepository.findAll(marksSpec);
      } else {
        marks = marksRepository.findAll();
      }
      return baseResponse.successResponse(marks);
    } catch (Exception e) {
      e.printStackTrace();
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> addUpdateMarksList(List<Marks> marksList) {
    try {
//      List<Marks> marksList = dtoList.stream().map(
//          dto -> Marks.builder()
//              .id(dto.getId())
//              .attendance(dto.getAttendance())
//              .marks(dto.getMarks())
//              .student(Student.builder().id(dto.getStudentId()).build())
//              .exam(Exam.builder().id(dto.getExamId()).build())
//              .course(Course.builder().id(dto.getCourseId()).build())
//              .examBatch(ExamBatch.builder().id(dto.getExamBatchId()).build())
//              .build()
//      ).collect(Collectors.toList());
      marksList.removeIf(Objects::isNull);
      marksRepository.saveAllAndFlush(marksList);
      return baseResponse.successResponse("Successfully saved marks list!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getMarksForExamBatch(String examBatchId) {
    List<Marks> marks = new ArrayList<>();
    try {
      marks = marksRepository.findAllByExamBatchIdOrderByStudentAsc(ValidationUtils.getLong(examBatchId));
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
    return baseResponse.successResponse(marks, "Marks for examBatch fetched successfully!");
  }
}
