package com.rba1aji.examinationmanagementsystem.repository.specification;

import com.rba1aji.examinationmanagementsystem.model.ExamBatch;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ExamBatchSpecification {

  public Specification<ExamBatch> findAllByFacultyIdAndExamIdAndCourseIdAndDepartmentIdOptional(long facultyId, long examId, int courseId) {
    return ((root, query, cb) -> {
      query.distinct(true);
      Predicate predicate = cb.equal(root.get("active"), true);
      if (facultyId > 0) {
        predicate = cb.and(predicate, cb.equal(root.get("faculty").get("id"), facultyId));
      }
      if (examId > 0) {
        predicate = cb.and(predicate, cb.equal(root.get("exam").get("id"), examId));
      }
      if (courseId > 0) {
        predicate = cb.and(predicate, cb.equal(root.get("course").get("id"), courseId));
      }
      return predicate;
    });
  }

}
