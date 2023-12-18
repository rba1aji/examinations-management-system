package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.Instant;

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

  private Character attendance;

  private int marks;

  @ManyToOne
  private Student student;

  @ManyToOne
  private Exam exam;

  @ManyToOne
  private Course course;

  @ManyToOne
  private ExamBatch examBatch;

  @OneToOne
  private EvaluationPaper evaluationPaper;

  @LastModifiedDate
  private Timestamp lastModifiedDate;

  @PrePersist
  void prePersist() {
    this.lastModifiedDate = Timestamp.from(Instant.now());
  }

}
