package com.rba1aji.examinationmanagementsystem.model;

import com.rba1aji.examinationmanagementsystem.dto.ExamBatchStudentsDto;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
public class ExamBatch {
  @Id
  @Column(name = "id")
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
  private Course courseList;

  @ManyToOne
  @JoinColumn(name = "fk_faculty_id")
  private Faculty facultyList;

  private String venue;
}
