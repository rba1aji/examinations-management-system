package com.rba1aji.examinationmanagementsystem.repository.specification;

import com.rba1aji.examinationmanagementsystem.dto.ExamMarksReportReqDto;
import com.rba1aji.examinationmanagementsystem.model.Marks;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MarksSpecification {

  public Specification<Marks> getAllByExamBatchIdAndStudentIdAndExamIdAndCourseIdOptional(long examBatchId, long studentId, long examId, int courseId,
                                                                                          long startEvaluationPaperNumber, long endEvaluationPaperNumber) {
    return ((root, query, cb) -> {
      Predicate predicate = cb.and();
      if (examBatchId > 0) {
        predicate = cb.and(predicate, cb.equal(root.get("examBatch").get("id"), examBatchId));
        query.orderBy(cb.asc(root.get("student").get("registerNumber")));
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
      if (startEvaluationPaperNumber > 0 && endEvaluationPaperNumber > 0) {
        predicate = cb.and(predicate, cb.between(root.get("evaluationPaper").get("number"), startEvaluationPaperNumber, endEvaluationPaperNumber));
        query.orderBy(cb.asc(root.get("evaluationPaper").get("number")));
      }
      return predicate;
    });
  }

  public Specification<Marks> findAllMarksByExamMarksReportFilters(ExamMarksReportReqDto dto) {
    return ((root, query, cb) -> {
      Predicate predicate = cb.and();
      if (dto.getExamId() > 0) {
        predicate = cb.and(predicate, cb.equal(root.get("exam").get("id"), dto.getExamId()));
      }
      if (ValidationUtils.isNotNullAndNotEmpty(dto.getExamBatchIdList())) {
        predicate = cb.and(predicate, cb.in(root.get("examBatch").get("id")).value(dto.getExamBatchIdList()));
      }
      if (ValidationUtils.isNotNullAndNotEmpty(dto.getDepartmentCodeList())) {
        predicate = cb.and(predicate, cb.in(root.get("student").get("department").get("code")).value(dto.getDepartmentCodeList()));
      }
      if (ValidationUtils.isNotNullAndNotEmpty(dto.getCourseIdList())) {
        predicate = cb.and(predicate, cb.in(root.get("course").get("id")).value(dto.getCourseIdList()));
      }
      if (ValidationUtils.isNotNullAndNotEmpty(dto.getFacultyIdList())) {
        predicate = cb.and(predicate, cb.in(root.get("examBatch").get("faculty").get("id")).value(dto.getFacultyIdList()));
      }
      return predicate;
    });
  }

}
