package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Student {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String password;

  @Column(unique = true)
  private String registerNumber;

  private Date dateOfBirth;

  private String fullName;

  @ManyToOne
  @JoinColumn(name = "fk_department_code")
  private Department department;

  private String section;

  private String batch;

  private String phone;
}