package com.rba1aji.examinationmanagementsystem.repository.specification;

import com.rba1aji.examinationmanagementsystem.model.Marks;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MarksSpecification {

  public Specification<Marks> getAllByExamBatchIdAndStudentIdAndExamIdAndCourseIdOptional(long examBatchId, long studentId, long examId, int courseId) {
    return ((root, query, cb) -> {
      Predicate predicate = cb.and();
      if (examBatchId > 0) {
        predicate = cb.and(predicate, cb.equal(root.get("examBatch").get("id"), examBatchId));
      }
      if (studentId > 0) {
        predicate = cb.and(predicate, cb.equal(root.get("student").get("id"), studentId));
      }
      if (examId > 0) {
        predicate = cb.and(predicate, cb.equal(root.get("exam").get("id"), examId));
      }
      if (courseId > 0) {
        predicate = cb.and(predicate, cb.equal(root.get("course").get("id"), courseId));
      }
      query.orderBy(cb.asc(root.get("student").get("registerNumber")));
      return predicate;
    });
  }

}
