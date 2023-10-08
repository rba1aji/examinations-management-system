package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.dto.RepoSaveResponseDto;
import com.rba1aji.examinationmanagementsystem.model.Course;
import com.rba1aji.examinationmanagementsystem.model.Department;
import com.rba1aji.examinationmanagementsystem.repository.CourseRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.ExcelCellUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CourseService {

  private final CourseRepository courseRepository;
  private final BaseResponse baseResponse;

  public ResponseEntity<?> saveUpdateCourse(Course course) {
    try {
      return baseResponse.successResponse(courseRepository.saveAndFlush(course));
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> excelRegisterCourses(MultipartFile file) {
    List<Object> responseList = new ArrayList<>();
    try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
      Sheet sheet = workbook.getSheetAt(0);
      sheet.removeRow(sheet.getRow(0));                                               // remove header row
      sheet.forEach(row -> {
        var response = new RepoSaveResponseDto();
        Course course = null;
        try {
          course = Course.builder()
              .code(ExcelCellUtils.getString(row.getCell(0)))
              .acronym(ExcelCellUtils.getString(row.getCell(1)))
              .name(ExcelCellUtils.getString(row.getCell(2)))
              .credit(ExcelCellUtils.getInt(row.getCell(3)))
              .semester(ExcelCellUtils.getInt(row.getCell(4)))
              .department(Department.builder().code(ExcelCellUtils.getString(row.getCell(5))).build())
              .batch(ExcelCellUtils.getString(row.getCell(6)))
              .active(true)
              .build();
          course = this.saveCourse(course);
          response.setStatus(true);
          response.setId(course.getCode());
          response.setMessage("Success");
        } catch (Exception e) {
          response.setId(course != null ? course.getCode() : null);
          response.setMessage(e.getMessage());
        }
        responseList.add(response);
      });
      return baseResponse.successResponse(responseList);
    } catch (Exception e) {
      log.info("Error in excelRegisterStudents(): ", e);
      return baseResponse.errorResponse(e);
    }
  }

  @Transactional
  public Course saveCourse(Course course) {
    return courseRepository.saveAndFlush(course);
  }

  public ResponseEntity<?> getAllCourses(String department, String semester, String batch) {
    try {
      List<Course> courseList = courseRepository.findAllByDepartmentCodeAndSemesterAndBatchOptional(department, semester, batch);
      return baseResponse.successResponse(courseList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getCourseByCode(String code) {
    try {
      var course = courseRepository.findByCode(code);
      if (course.isPresent()) {
        return baseResponse.successResponse(course);
      }
      return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "Course not found!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
