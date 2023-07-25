package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Student {
  @Id
  private long id;

  private String dateOfBirth;

  private String fullName;

  private String section;

  private String batch;

  private String phone;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;
}