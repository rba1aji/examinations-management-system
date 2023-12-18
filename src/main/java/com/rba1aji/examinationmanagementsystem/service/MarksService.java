package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.dto.ExamMarksReportReqDto;
import com.rba1aji.examinationmanagementsystem.model.Evaluation;
import com.rba1aji.examinationmanagementsystem.model.EvaluationBundle;
import com.rba1aji.examinationmanagementsystem.model.ExamBatch;
import com.rba1aji.examinationmanagementsystem.model.Marks;
import com.rba1aji.examinationmanagementsystem.repository.EvaluationBundleRepository;
import com.rba1aji.examinationmanagementsystem.repository.EvaluationRepository;
import com.rba1aji.examinationmanagementsystem.repository.ExamBatchRepository;
import com.rba1aji.examinationmanagementsystem.repository.MarksRepository;
import com.rba1aji.examinationmanagementsystem.repository.specification.MarksSpecification;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
  private final ExamBatchRepository examBatchRepository;
  private final EvaluationRepository evaluationRepository;
  private final EvaluationBundleRepository evaluationBundleRepository;

  public ResponseEntity<?> getAllMarksByOptionalParams(String examBatchId, String studentId, String examId, String courseId, int evaluationId, int evaluationBundleId) {
    try {
      Evaluation evaluation = evaluationId == 0 ? null : evaluationRepository.findById(evaluationId).orElseThrow();
      EvaluationBundle evaluationBundle = evaluationBundleId == 0 ? null : evaluationBundleRepository.findById(evaluationBundleId).orElseThrow();
      long startPaperNumber = 0;
      long endPaperNumber = 0;
      if (evaluation != null) {
        startPaperNumber = evaluation.getStartPaperNumber();
        endPaperNumber = evaluation.getEndPaperNumber();
      } else if (evaluationBundle != null) {
        startPaperNumber = evaluationBundle.getEvaluationPaperList().get(0).getNumber();
        endPaperNumber = evaluationBundle.getEvaluationPaperList().get(evaluationBundle.getEvaluationPaperList().size() - 1).getNumber();
      }

      Specification<Marks> marksSpec = marksSpecification.getAllByExamBatchIdAndStudentIdAndExamIdAndCourseIdOptional(
        ValidationUtils.getLong(examBatchId),
        ValidationUtils.getLong(studentId),
        ValidationUtils.getLong(examId),
        ValidationUtils.getInt(courseId),
        startPaperNumber, endPaperNumber
      );
      List<Marks> marks = marksRepository.findAll(marksSpec);
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

  public List<Marks> getAllMarksForExamMarksReport(ExamMarksReportReqDto reqDto) {
    List<Marks> marks = new ArrayList<>();
    try {
      marks = marksRepository.findAll(marksSpecification.findAllMarksByExamMarksReportFilters(reqDto));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return marks;
  }

  public ResponseEntity<?> addUpdateMarksListForExamBatch(List<Marks> marksList, String examBatchId) {
    try {
      ExamBatch examBatch = examBatchRepository.findById(ValidationUtils.getLong(examBatchId)).orElse(null);
      if (examBatch == null) {
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Exam batch not found!");
      }
      if (examBatch.isDisableMarksEntry()) {
        return baseResponse.errorResponse(HttpStatus.NOT_ACCEPTABLE, "Marks entry disabled!");
      }
      return addUpdateMarksList(marksList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
