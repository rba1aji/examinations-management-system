package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.BatchStudent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class StudentServiceTest {

  @Autowired
  private StudentService studentService;

//  @Test
//  void getStudentsForRegisterNumbersRange() {
//    Set<BatchStudent> batchStudents = new HashSet<>();
//    batchStudents.add(BatchStudent.builder().startRegNum("73772014108").endRegNum("73772014110").build());
//    Assertions.assertNotEquals(0,
//        studentService.getStudentsForRegisterNumbersRange(batchStudents).size()
//    );
//  }
}