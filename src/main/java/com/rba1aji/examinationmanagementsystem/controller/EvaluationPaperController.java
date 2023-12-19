package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.model.SplitUpMarks;
import com.rba1aji.examinationmanagementsystem.service.EvaluationPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/evaluation-paper")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluationPaperController {

  private final EvaluationPaperService evaluationPaperService;

  @PostMapping("/v1/submit-evaluation-paper-marks/{evaluationId}/{paperId}")
  public ResponseEntity<?> saveEvaluationPaperMarks(@PathVariable("evaluationId") int evaluationId,
                                                    @PathVariable("paperId") int paperId,
                                                    @RequestBody List<SplitUpMarks> marks) {
    return evaluationPaperService.submitEvaluationPaperMarks(evaluationId, paperId, marks);
  }

  @PutMapping("/v1/enable-entry-for-evaluation-papers")
  public ResponseEntity<?> enableEntryForEvaluationPapers(@RequestBody List<Integer> paperIds) {
    return evaluationPaperService.enableEntryForEvaluationPapers(paperIds);
  }

}
