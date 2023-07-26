package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Faculty {
  @Id
  private long id;

  private String password;

  private String fullName;

  private String designation;

  private String email;

  private String phone;

  @ManyToOne
  @JoinColumn(name = "fk_department_id")
  private Department department;
}
