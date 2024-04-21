package com.rba1aji.examinationmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "config")
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(unique = true, nullable = false)
  private String code;

  private String acronym;

  private String name;

  private int credit;

  private int semester;

  @ManyToOne
  @JoinColumn
  private Department department;

  private String batch;

  @JsonIgnore
  private boolean active = true;
}
