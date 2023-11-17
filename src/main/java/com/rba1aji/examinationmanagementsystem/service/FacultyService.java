package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.repository.FacultyRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacultyService {

  private final FacultyRepository facultyRepository;
  private final BaseResponse baseResponse;

  public ResponseEntity<?> getAllFaculties() {
    try {
      List<Faculty> facultyList = facultyRepository.findAll();
      return baseResponse.successResponse(facultyList);
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
