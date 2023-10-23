package com.rba1aji.examinationmanagementsystem.repository;

import com.rba1aji.examinationmanagementsystem.model.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long>, JpaSpecificationExecutor<Marks> {
}
