package com.rba1aji.examinationmanagementsystem.repository;

import com.rba1aji.examinationmanagementsystem.model.EvaluationPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationPaperRepository extends JpaRepository<EvaluationPaper, Integer> {

  List<EvaluationPaper> findAllByNumberIn(List<Long> numbers);

}
