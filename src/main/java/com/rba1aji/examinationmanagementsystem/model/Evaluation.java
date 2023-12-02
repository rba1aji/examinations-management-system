package com.rba1aji.examinationmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Course wise evaluation.
 */
@Entity
@Data
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @ManyToOne
    private Exam exam;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Faculty faculty;

}
