package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.EvaluationPaper;
import com.rba1aji.examinationmanagementsystem.model.EvaluationPaperMarks;
import com.rba1aji.examinationmanagementsystem.repository.EvaluationPaperRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluationPaperService {

  private final EvaluationPaperRepository evaluationPaperRepository;
  public final BaseResponse baseResponse;

  public ResponseEntity<?> submitEvaluationPaperMarks(int paperId, List<EvaluationPaperMarks> marks) {
    EvaluationPaper evaluationPaper = evaluationPaperRepository.findById(paperId).orElse(null);
    if (evaluationPaper == null) {
      return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Evaluation paper not found!");
    }
    if (evaluationPaper.isDisableEntry()) {
      return baseResponse.errorResponse(HttpStatus.NOT_ACCEPTABLE, "Evaluation paper marks entry disabled!");
    }
    evaluationPaper.setEvaluationPaperMarks(marks);
    evaluationPaper.setDisableEntry(true);
    evaluationPaperRepository.saveAndFlush(evaluationPaper);
    return baseResponse.successResponse(evaluationPaper, "Evaluation marks submitted successfully for paper: " + evaluationPaper.getNumber());
  }

}
