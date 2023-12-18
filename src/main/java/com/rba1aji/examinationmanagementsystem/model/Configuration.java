package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Configuration {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private String name;

  private String type;

  @Column(length = 8000 , nullable = false)
  private String configJson;

  private boolean active;

  @LastModifiedDate
  private Timestamp lastModifiedDate;

  @PrePersist
  void prePersist() {
    this.lastModifiedDate = Timestamp.from(Instant.now());
  }

}

////sectionsCount
//sections:
//[
//{
//  //questionsCount
//  answerableQuestionsCount:
//  questions:[
//    { question: maxMark: co: }
//  ]
//}
//]
