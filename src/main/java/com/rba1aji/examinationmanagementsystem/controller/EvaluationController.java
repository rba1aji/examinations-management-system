package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.constant.UserRole;
import com.rba1aji.examinationmanagementsystem.dto.request.CreateEvaluationReqDto;
import com.rba1aji.examinationmanagementsystem.security.AllowedRoles;
import com.rba1aji.examinationmanagementsystem.service.EvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping("/v1/create-evaluation-for-course")
  @AllowedRoles(UserRole.ADMIN)
  public ResponseEntity<?> createEvaluationForCourse(@RequestBody CreateEvaluationReqDto reqDto) {
    return evaluationService.createEvaluationForCourse(reqDto);
  }

//  @Deprecated
//  @GetMapping("/v1/get-evaluation-bundles")
//  public ResponseEntity<?> getEvaluationBundles(@RequestParam("examId") long examId,
//                                                @RequestParam("courseId") int courseId) {
//    return evaluationService.getAllEvaluationBundleForExamAndCourse(examId, courseId);
//  }

  @GetMapping("/v1/get-all-evaluation")
  @AllowedRoles({UserRole.ADMIN, UserRole.FACULTY})
  public ResponseEntity<?> getAllEvaluation(@RequestParam(value = "examId", required = false) List<Integer> examId,
                                            @RequestParam(value = "courseId", required = false) List<Integer> courseId,
                                            @RequestParam(value = "facultyId", required = false) List<Integer> facultyId,
                                            @RequestParam(value = "sessionFaculty", required = false) boolean sessionFaculty) {
    return evaluationService.getAllEvaluationForExamIdCourseId(examId, courseId, facultyId, sessionFaculty);
  }


}
