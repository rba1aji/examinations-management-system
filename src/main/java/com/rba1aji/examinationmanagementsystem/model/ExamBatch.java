package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.sql.Array;
import java.sql.Timestamp;

@Data
@Entity
public class ExamBatch {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  private Timestamp startTime;

  private Timestamp endTime;

  private String students;   // parse to List<ExamBatchStudentsDto>

  @ManyToOne
  @JoinColumn(name = "fk_exam_id")
  private Exam exam;

  @ManyToOne
  @JoinColumn(name = "fk_course_id")
  private Course course;

  @ManyToOne
  @JoinColumn(name = "fk_faculty_id")
  private Faculty faculty;

  private String venue;
}
