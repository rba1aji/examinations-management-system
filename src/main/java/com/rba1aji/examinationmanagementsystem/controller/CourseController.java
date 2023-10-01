package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {

  private final CourseService courseService;

  @GetMapping("/get-all-courses")
  public ResponseEntity<?> getAllCourses(@RequestParam(name = "department", required = false) String departments,
                                         @RequestParam(name = "semester", required = false) String semesters,
                                         @RequestParam(name = "batch", required = false) String batches) {
    return courseService.getAllCourses(departments, semesters, batches);
  }

  @GetMapping("/get-course")
  public ResponseEntity<?> getCourse(@RequestParam(value = "code") String courseCode) {
    return courseService.getCourseByCode(courseCode);
  }

}
