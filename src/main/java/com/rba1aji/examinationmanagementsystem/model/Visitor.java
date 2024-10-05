package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Visitor {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String details;

  private Timestamp time;

  @PrePersist
  public void prePersist() {
    this.time = new Timestamp(System.currentTimeMillis());
  }

}
