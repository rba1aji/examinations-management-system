package com.rba1aji.examinationmanagementsystem.repository;

import com.rba1aji.examinationmanagementsystem.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer>, JpaSpecificationExecutor<Evaluation> {
  Evaluation findFirstByExamIdAndCourseIdAndActive(long examId, int courseId, boolean active);

  List<Evaluation> findAllByExamIdAndCourseIdAndActive(long examId, int courseId, boolean active);
}
