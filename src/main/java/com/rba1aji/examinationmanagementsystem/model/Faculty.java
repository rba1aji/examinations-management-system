package com.rba1aji.examinationmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Faculty {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private long id;

  @NotBlank
  @Column(unique = true)
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String fullName;

  @NotBlank
  private String designation;

  private String email;

  private String phone;

  @ManyToOne
  @JoinColumn(name = "fk_department_id" )
  private Department department;
}
