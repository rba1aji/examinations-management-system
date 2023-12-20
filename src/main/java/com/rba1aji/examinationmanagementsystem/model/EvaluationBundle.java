package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationBundle {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @OneToMany(cascade = CascadeType.ALL)
  @OrderBy("number")
  private List<EvaluationPaper> evaluationPaperList;

  @ManyToOne
  private Evaluation evaluation;

  private boolean disableEntry;

  private Boolean disablePrintout;
}
