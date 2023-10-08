package com.rba1aji.examinationmanagementsystem.repository.implementation;

import com.rba1aji.examinationmanagementsystem.model.Course;
import com.rba1aji.examinationmanagementsystem.utilities.CommonUtils;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseRepositoryImpl {

  @PersistenceContext
  private EntityManager entityManager;

  public List<Course> findAllByDepartmentCodeAndSemesterAndBatchOptional(String departmentCode, String semester, String batch) {
    String sql = "FROM Course WHERE active = true";
    if (ValidationUtils.areNotNullAndNotEmpty(departmentCode)) {
      sql += " AND department.code  IN (" + CommonUtils.commaSeperatedSqlString(departmentCode) + ")";
    }
    if (ValidationUtils.areNotNullAndNotEmpty(semester)) {
      sql += " AND semester IN (" + semester + ")";
    }
    if (ValidationUtils.areNotNullAndNotEmpty(batch)) {
      sql += " AND batch IN (" + CommonUtils.commaSeperatedSqlString(batch) + ")";
    }
    TypedQuery<Course> query = entityManager.createQuery(sql, Course.class);
    return query.getResultList();
  }

}
