package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Department {
  @Id
  private String id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "degree_id")
  private Degree degree;
}
