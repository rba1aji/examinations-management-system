package com.rba1aji.examinationmanagementsystem.repository.specification;

import com.rba1aji.examinationmanagementsystem.model.Exam;
import com.rba1aji.examinationmanagementsystem.utilities.CommonUtils;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ExamSpecifications {

  public Specification<Exam> findAllByBatchAndSemesterOptional(String batch, int semester) {
    return (root, query, cb) -> {
      query.distinct(true);
      Predicate predicate = cb.equal(root.get("active"), true);
      if (!ValidationUtils.isNotNullAndNotEmpty(batch)) {
        predicate = cb.and(predicate, cb.in(root.get("batch")).value(CommonUtils.commaSeperatedSqlString(batch)));
      }
      if (semester > 0) {
        predicate = cb.and(predicate, cb.in(root.get("semester")).value(semester));
      }
      return predicate;
    };
  }

}
