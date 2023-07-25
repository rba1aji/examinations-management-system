package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String code;

  private String name;

  private int credit;

  private int semester;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  private String batch;
}
