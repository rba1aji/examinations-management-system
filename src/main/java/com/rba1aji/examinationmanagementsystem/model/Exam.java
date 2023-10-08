package com.rba1aji.examinationmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Exam {
  @Id
  private long id;

  @NotBlank
  private String name;

  private int semester;

  @NotBlank
  private String batch;

  @JsonIgnore
  private boolean active = true;

}
