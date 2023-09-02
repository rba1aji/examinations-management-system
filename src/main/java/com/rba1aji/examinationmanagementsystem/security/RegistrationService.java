package com.rba1aji.examinationmanagementsystem.security;

import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.repository.FacultyRepository;
import com.rba1aji.examinationmanagementsystem.repository.StudentRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegistrationService {

  private final BaseResponse      baseResponse;
  private final FacultyRepository facultyRepository;
  private final StudentRepository studentRepository;

  public ResponseEntity<?> registerFaculty(Faculty faculty) {
    try {
      if (facultyRepository.findByUsername(faculty.getUsername()).isPresent())
        return baseResponse.errorResponse("Faculty with this username already exists!", HttpStatus.UNPROCESSABLE_ENTITY);
      facultyRepository.save(faculty);
      return baseResponse.successResponse("Faculty registered successfully!" );
    } catch (Exception e) {
      e.printStackTrace();
      return baseResponse.errorResponse(e);
    }
  }

}
