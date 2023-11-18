package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Admin extends User {
  @Id
  private int id;

  private String username;

  private String password;

  private String fullName;
}
