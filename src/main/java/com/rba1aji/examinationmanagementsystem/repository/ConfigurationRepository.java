package com.rba1aji.examinationmanagementsystem.repository;

import com.rba1aji.examinationmanagementsystem.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
  List<Configuration> findAllByActive(boolean active);
}
