package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.EvaluationBundle;
import com.rba1aji.examinationmanagementsystem.repository.EvaluationBundleRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluationBundleService {

  public final EvaluationBundleRepository evaluationBundleRepository;
  private final BaseResponse baseResponse;

  public ResponseEntity<?> getAllEvaluationBundleForEvaluationId(int evaluationId) {
    try {
      List<EvaluationBundle> evaluationBundleList = evaluationBundleRepository.findAllByEvaluationId(evaluationId);
      return baseResponse.successResponse(evaluationBundleList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getEvaluationBundleForId(int evaluationBundleId) {
    try {
      return baseResponse.successResponse(
        evaluationBundleRepository.findById(evaluationBundleId)
          .orElseThrow(() -> new Exception("Evaluation Bundle not found for id: " + evaluationBundleId))
      );
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
