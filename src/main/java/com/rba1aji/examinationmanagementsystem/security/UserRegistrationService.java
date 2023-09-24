package com.rba1aji.examinationmanagementsystem.security;

import com.rba1aji.examinationmanagementsystem.dto.RepoSaveErrorDto;
import com.rba1aji.examinationmanagementsystem.model.Department;
import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.model.Student;
import com.rba1aji.examinationmanagementsystem.repository.FacultyRepository;
import com.rba1aji.examinationmanagementsystem.repository.StudentRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.EncryptionUtils;
import com.rba1aji.examinationmanagementsystem.utilities.ExcelCellUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserRegistrationService {

  private final BaseResponse      baseResponse;
  private final FacultyRepository facultyRepository;
  private final StudentRepository studentRepository;

  @Transactional
  public ResponseEntity<?> registerFaculty(Faculty faculty) {
    try {
      if (facultyRepository.findByUsername(faculty.getUsername()).isPresent())
        return baseResponse.errorResponse("Faculty with this username already exists!", HttpStatus.UNPROCESSABLE_ENTITY);
      faculty.setPassword(EncryptionUtils.encrypt(faculty.getPassword()));
      facultyRepository.save(faculty);
      return baseResponse.successResponse("Faculty registered successfully!");
    } catch (Exception e) {
      e.printStackTrace();
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> excelRegisterStudents(MultipartFile file) {
    List<Object> responseList = new ArrayList<>();
    try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
      Sheet sheet = workbook.getSheetAt(0);
      sheet.removeRow(sheet.getRow(0));                                               // remove header row
      sheet.forEach(row -> {
        try {
          Student student = Student.builder()
              .registerNumber(ExcelCellUtils.getString(row.getCell(0)))
              .dateOfBirth(ExcelCellUtils.getDate(row.getCell(1)))
              .fullName(ExcelCellUtils.getString(row.getCell(2)))
              .department(Department.builder().code(ExcelCellUtils.getString(row.getCell(3))).build())
              .section(ExcelCellUtils.getString(row.getCell(4)))
              .batch(ExcelCellUtils.getString(row.getCell(5)))
              .phone(ExcelCellUtils.getString(row.getCell(6)))
              .password(EncryptionUtils.encrypt(ExcelCellUtils.getDateString(row.getCell(1))))
              .build();
          try {
            student = this.registerStudent(student);
            responseList.add(student);
          } catch (DataIntegrityViolationException e) {
            var error = new RepoSaveErrorDto();
            error.setId(student.getRegisterNumber());
            error.setMessage("Already exists!");
            responseList.add(error);
          } catch (InvalidDataAccessApiUsageException e) {
            var error = new RepoSaveErrorDto();
            error.setId(student.getRegisterNumber());
            error.setMessage("Invalid data!");
            responseList.add(error);
          }
        } catch (Exception e) {
          var error = new RepoSaveErrorDto();
          error.setMessage(e.getMessage());
          responseList.add(error);
        }
      });
      return baseResponse.successResponse(responseList);
    } catch (Exception e) {
      log.info("Error in excelRegisterStudents(): ", e);
      return baseResponse.errorResponse(e);
    }
  }

  @Transactional
  public Student registerStudent(Student student) {
    return studentRepository.saveAndFlush(student);
  }

  public ResponseEntity<?> excelRegisterFaculties(MultipartFile file) {
    return null;
  }
}
