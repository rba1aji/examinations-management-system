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
  private long id;

  private String name;

  private Timestamp startTime;

  private Timestamp endTime;

  private String students;   // parse to List<ExamBatchStudentsDto>

  @ManyToOne
  @JoinColumn(name = "exam_id")
  private Exam exam;

  @OneToMany
  @JoinColumn(name = "course_id")
  private List<Course> courseList;

  @OneToMany
  @JoinColumn(name = "faculty_id")
  private List<Faculty> facultyList;

  private String venue;
}
