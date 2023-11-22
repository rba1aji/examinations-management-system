package com.rba1aji.examinationmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamBatch {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;

  @ManyToMany(cascade = CascadeType.ALL)
  private Set<BatchStudent> students;

  @ManyToOne
  @JoinColumn
  private Exam exam;

  @ManyToOne
  @JoinColumn
  private Course course;

  @ManyToOne
  @JoinColumn
  private Faculty faculty;

  private Timestamp startTime;

  private Timestamp endTime;

  private String venue;

  private boolean marksSubmitted;

  @JsonIgnore
  private boolean active = true;
}
