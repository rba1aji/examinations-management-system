package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.model.EvaluationPaperMarks;
import com.rba1aji.examinationmanagementsystem.service.EvaluationPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/evaluation-paper")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluationPaperController {

  private final EvaluationPaperService evaluationPaperService;

  @PostMapping("/v1/submit-evaluation-paper-marks/{paperId}")
  public ResponseEntity<?> saveEvaluationPaperMarks(@PathVariable("paperId") int paperId, @RequestBody List<EvaluationPaperMarks> marks) {
    return evaluationPaperService.submitEvaluationPaperMarks(paperId, marks);
  }

}
