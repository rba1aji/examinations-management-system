package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.dto.request.CreateEvaluationReqDto;
import com.rba1aji.examinationmanagementsystem.model.Configuration;
import com.rba1aji.examinationmanagementsystem.model.Course;
import com.rba1aji.examinationmanagementsystem.model.Evaluation;
import com.rba1aji.examinationmanagementsystem.model.EvaluationBundle;
import com.rba1aji.examinationmanagementsystem.model.EvaluationPaper;
import com.rba1aji.examinationmanagementsystem.model.Exam;
import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.repository.EvaluationBundleRepository;
import com.rba1aji.examinationmanagementsystem.repository.EvaluationRepository;
import com.rba1aji.examinationmanagementsystem.repository.specification.EvaluationSpecification;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rba1aji
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluationService {

  private final EvaluationRepository evaluationRepository;
  private final EvaluationBundleRepository evaluationBundleRepository;
  private final BaseResponse baseResponse;
  private final EvaluationSpecification evaluationSpecification;
  private final HttpServletRequest request;

  public ResponseEntity<?> createEvaluationForCourse(CreateEvaluationReqDto reqDto) {
    try {
      Evaluation evaluation = Evaluation.builder()
        .exam(Exam.builder().id(reqDto.getExamId()).build())
        .course(Course.builder().id(reqDto.getCourseId()).build())
        .faculty(Faculty.builder().id(reqDto.getFacultyId()).build())
        .startPaperNumber(reqDto.getStartPaperNumber())
        .endPaperNumber(reqDto.getEndPaperNumber())
        .questionPaperConfig(Configuration.builder().id(reqDto.getQuestionPaperConfigId()).build())
        .active(true)
        .build();
      evaluationRepository.saveAndFlush(evaluation);
      List<EvaluationPaper> evaluationPaperList = new ArrayList<>();
      List<EvaluationBundle> evaluationBundleList = new ArrayList<>();
      for (long number = reqDto.getStartPaperNumber(); number <= reqDto.getEndPaperNumber(); number++) {
        evaluationPaperList.add(
          EvaluationPaper.builder()
            .number(number)
            .build()
        );
        if (evaluationPaperList.size() == 25 || number == reqDto.getEndPaperNumber()) {
          evaluationBundleList.add(
            EvaluationBundle.builder()
              .evaluation(evaluation)
              .evaluationPaperList(evaluationPaperList)
              .build()
          );
          evaluationPaperList = new ArrayList<>();
        }
      }
      evaluationBundleRepository.saveAll(evaluationBundleList);
      return baseResponse.successResponse(evaluation, "Successfully created evaluation!");
    } catch (Exception e) {
      e.printStackTrace();
      return baseResponse.errorResponse(e);
    }
  }

  @Deprecated
  public ResponseEntity<?> getAllEvaluationBundleForExamAndCourse(long examId, int courseId) {
    Evaluation evaluation = evaluationRepository.findFirstByExamIdAndCourseIdAndActive(examId, courseId, true);
    if (evaluation == null) {
      return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Evaluation not found!");
    }
    List<EvaluationBundle> evaluationBundleList = evaluationBundleRepository.findAllByEvaluationId(evaluation.getId());
    return baseResponse.successResponse(evaluationBundleList);
  }

  public ResponseEntity<?> getAllEvaluationForExamIdCourseId(List<Integer> examId, List<Integer> courseId, List<Integer> facultyId, boolean sessionFaculty) {
    try {
      if (sessionFaculty) {
        Integer sessionUserId = (Integer) request.getAttribute("userId");
        facultyId = Collections.singletonList(sessionUserId);
      }
      List<Evaluation> evaluationList = evaluationRepository.findAll(
        evaluationSpecification.findAllByExamIdAndCourseIdAndFacultyIdOptional(
          examId, courseId, facultyId
        )
      );
      return baseResponse.successResponse(evaluationList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> deleteEvaluation(int evaluationId) {
    try {
      Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow();
      evaluation.setActive(false);
      evaluationRepository.save(evaluation);
      return baseResponse.successResponse(null, "Successfully deleted evaluation!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
