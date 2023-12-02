package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class EvaluationBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String startPaperNumber;

    private String endPaperNumber;

    @OneToMany
    private List<EvaluationPaper> evaluationPaperList;

    @ManyToOne
    private Evaluation evaluation;

    private boolean disableEntry;

}
