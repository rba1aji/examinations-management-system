package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(schema = "user")
public class Admin extends User {
  @Id
  private int id;

  private String username;

  private String password;

  private String fullName;
}
