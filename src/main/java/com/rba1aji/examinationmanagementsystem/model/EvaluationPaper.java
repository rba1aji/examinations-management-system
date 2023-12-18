package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationPaper {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private long number;

  @ManyToMany(cascade = CascadeType.ALL)
  private List<SplitUpMarks> splitUpMarks;

  private Integer totalMarks;

  private Boolean submitted;

  private boolean disableEntry;

  @LastModifiedDate
  private Timestamp lastModifiedDate;

  @PrePersist
  void prePersist() {
    this.lastModifiedDate = Timestamp.from(Instant.now());
  }

}