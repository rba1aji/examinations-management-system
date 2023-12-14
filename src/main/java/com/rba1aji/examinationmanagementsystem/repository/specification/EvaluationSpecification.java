package com.rba1aji.examinationmanagementsystem.repository.specification;

import com.rba1aji.examinationmanagementsystem.model.Evaluation;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EvaluationSpecification {

  public Specification<Evaluation> findAllByExamIdAndCourseIdAndFacultyIdOptional(List<Integer> examId, List<Integer> courseId, List<Integer> facultyId) {
    return ((root, query, cb) -> {
      Predicate predicate = cb.equal(root.get("active"), (true));
      if (ValidationUtils.isNotNullAndNotEmpty(examId)) {
        predicate = cb.and(predicate, cb.in(root.get("exam").get("id")).value(examId));
      }
      if (ValidationUtils.isNotNullAndNotEmpty(courseId)) {
        predicate = cb.and(predicate, cb.in(root.get("course").get("id")).value(courseId));
      }
      if (ValidationUtils.isNotNullAndNotEmpty(facultyId)) {
        predicate = cb.and(predicate, cb.in(root.get("faculty").get("id")).value(facultyId));
      }
      return predicate;
    });
  }

}
