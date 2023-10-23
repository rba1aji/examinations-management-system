package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "exam_id", "course_id"})
    }
)
public class Marks {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private char attendance;

  private int marks;

  @ManyToOne
  private Student student;

  @ManyToOne
  private Exam exam;

  @ManyToOne
  private Course course;

  @ManyToOne
  private ExamBatch examBatch;

}
