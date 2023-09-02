package com.rba1aji.examinationmanagementsystem.repository;

import com.rba1aji.examinationmanagementsystem.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
  Optional<Faculty> findByUsername(String username);
}
