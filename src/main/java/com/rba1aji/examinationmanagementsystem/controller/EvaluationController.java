package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.dto.request.CreateEvaluationReqDto;
import com.rba1aji.examinationmanagementsystem.model.EvaluationPaperMarks;
import com.rba1aji.examinationmanagementsystem.service.EvaluationPaperService;
import com.rba1aji.examinationmanagementsystem.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluationController {

  private final EvaluationService evaluationService;
  private final EvaluationPaperService evaluationPaperService;

  @PostMapping("/v1/create-evaluation-for-course")
  public ResponseEntity<?> createEvaluationForCourse(@RequestBody CreateEvaluationReqDto reqDto) {
    return evaluationService.createEvaluationForCourse(reqDto);
  }

  @Deprecated
  @GetMapping("/v1/get-evaluation-bundles")
  public ResponseEntity<?> getEvaluationBundles(@RequestParam("examId") long examId,
                                                @RequestParam("courseId") int courseId) {
    return evaluationService.getAllEvaluationBundleForExamAndCourse(examId, courseId);
  }

  @GetMapping("/v1/get-all-evaluation-for-exam-course")
  public ResponseEntity<?> getAllEvaluation(@RequestParam("examId") long examId, @RequestParam("courseId") int courseId) {
    return evaluationService.getAllEvaluationForExamIdCourseId(examId, courseId);
  }

  @GetMapping("/v2/get-evaluation-bundles")
  public ResponseEntity<?> getAllEvaluationBundles(@RequestParam("evaluationId") int evaluationId){
    return evaluationService.getAllEvaluationBundleForEvaluationId(evaluationId);
  }

  @PostMapping("/v1/submit-evaluation-paper-marks/{paperId}")
  public ResponseEntity<?> saveEvaluationPaperMarks(@PathVariable("paperId") int paperId, @RequestBody List<EvaluationPaperMarks> marks) {
    return evaluationPaperService.submitEvaluationPaperMarks(paperId, marks);
  }

}
