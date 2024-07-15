package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class DatabaseKeepAlive {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @LastModifiedDate
  private Timestamp lastModifiedDate;

  @PrePersist
  void prePersist() {
    this.lastModifiedDate = Timestamp.from(Instant.now());
  }

}
