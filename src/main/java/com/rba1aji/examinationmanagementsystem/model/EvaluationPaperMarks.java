package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EvaluationPaperMarks {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String section;
  private String question;
  private int marks;
}
