package com.rba1aji.examinationmanagementsystem.repository.impl;

import com.rba1aji.examinationmanagementsystem.model.Course;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseRepositoryImpl {

  private final EntityManager entityManager;

  public List<Course> findAllByOptionalFilters(String departmentCode, String semester, String batch) {
    String sql = "FROM Course WHERE active = true";
    if (ValidationUtils.areNotNullAndNotEmpty(departmentCode)) {
      sql += " AND department.code  IN (" + departmentCode + ")";
    }
    if (ValidationUtils.areNotNullAndNotEmpty(semester)) {
      sql += " AND semester IN (" + semester + ")";
    }
    if (ValidationUtils.areNotNullAndNotEmpty(batch)) {
      sql += " AND batch IN (" + batch + ")";
    }
    TypedQuery<Course> query = entityManager.createQuery(sql, Course.class);
    return query.getResultList();
  }

}
