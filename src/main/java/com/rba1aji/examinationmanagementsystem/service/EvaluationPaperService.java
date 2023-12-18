package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.Evaluation;
import com.rba1aji.examinationmanagementsystem.model.EvaluationPaper;
import com.rba1aji.examinationmanagementsystem.model.SplitUpMarks;
import com.rba1aji.examinationmanagementsystem.model.Marks;
import com.rba1aji.examinationmanagementsystem.repository.EvaluationPaperRepository;
import com.rba1aji.examinationmanagementsystem.repository.EvaluationRepository;
import com.rba1aji.examinationmanagementsystem.repository.MarksRepository;
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
  private final EvaluationRepository evaluationRepository;
  public final MarksRepository marksRepository;

  public ResponseEntity<?> submitEvaluationPaperMarks(int evaluationId, int paperId, List<SplitUpMarks> splitUpMarks) {
    try {
      Evaluation evaluation = evaluationRepository.findByIdAndActive(evaluationId, true).orElseThrow();
      EvaluationPaper evaluationPaper = evaluationPaperRepository.findById(paperId).orElseThrow();
      if (evaluationPaper.isDisableEntry()) {
        return baseResponse.errorResponse(HttpStatus.NOT_ACCEPTABLE, "Evaluation paper marks entry disabled!");
      }
      evaluationPaper.setSplitUpMarks(splitUpMarks);
      evaluationPaper.setSubmitted(true);
      evaluationPaper.setDisableEntry(true);
      evaluationPaperRepository.saveAndFlush(evaluationPaper);
      int totalMarks = splitUpMarks.stream().mapToInt(SplitUpMarks::getMarks).sum();
      evaluationPaper.setTotalMarks(totalMarks);
      Marks marks = Marks.builder().
        evaluationPaper(evaluationPaper)
        .marks(totalMarks)
        .exam(evaluation.getExam())
        .course(evaluation.getCourse())
        .build();
      marksRepository.saveAndFlush(marks);
      return baseResponse.successResponse(marks, "Evaluation marks submitted successfully for paper: " + evaluationPaper.getNumber());
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }
}
