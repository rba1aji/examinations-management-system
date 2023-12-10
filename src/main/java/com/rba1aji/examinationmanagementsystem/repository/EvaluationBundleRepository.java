package com.rba1aji.examinationmanagementsystem.repository;

import com.rba1aji.examinationmanagementsystem.model.EvaluationBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationBundleRepository extends JpaRepository<EvaluationBundle, Integer> {
  List<EvaluationBundle> findAllByEvaluationId(int evaluationId);
}
