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
  public ResponseEntity<?> getAllCourses(@RequestParam(value = "department", required = false) String department,
                                         @RequestParam(value = "semester", required = false) String semester,
                                         @RequestParam(value = "batch", required = false) String batch) {
    return courseService.getAllCourses(department, semester, batch);
  }

}
