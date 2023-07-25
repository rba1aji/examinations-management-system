package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Exam {
  @Id
  private long id;

  private String name;

  private int semester;

  private String batch;
}
