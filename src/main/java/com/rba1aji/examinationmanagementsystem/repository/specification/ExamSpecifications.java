package com.rba1aji.examinationmanagementsystem.repository.specification;

import com.rba1aji.examinationmanagementsystem.model.Exam;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExamSpecifications {

  public Specification<Exam> findAllByBatchAndSemesterOptional(List<String> batch, List<Integer> semester) {
    return (root, query, cb) -> {
      query.distinct(true);
      Predicate predicate = cb.equal(root.get("active"), true);
      if (ValidationUtils.isNotNullAndNotEmpty(batch)) {
        predicate = cb.and(predicate, cb.in(root.get("batch")).value(batch));
      }
      if (ValidationUtils.isNotNullAndNotEmpty(semester)) {
        predicate = cb.and(predicate, cb.in(root.get("semester")).value(semester));
      }
      return predicate;
    };
  }

}
