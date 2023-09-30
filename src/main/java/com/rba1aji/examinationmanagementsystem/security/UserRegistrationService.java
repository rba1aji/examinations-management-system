package com.rba1aji.examinationmanagementsystem.security;

import com.rba1aji.examinationmanagementsystem.dto.RepoSaveResponseDto;
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

  private final BaseResponse baseResponse;
  private final FacultyRepository facultyRepository;
  private final StudentRepository studentRepository;

  @Transactional
  public ResponseEntity<?> registerSingleFaculty(Faculty faculty) {
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
        var response = new RepoSaveResponseDto();
        Student student = null;
        try {
          student = Student.builder()
              .registerNumber(ExcelCellUtils.getString(row.getCell(0)))
              .dateOfBirth(ExcelCellUtils.getDate(row.getCell(1)))
              .fullName(ExcelCellUtils.getString(row.getCell(2)))
              .department(Department.builder().code(ExcelCellUtils.getString(row.getCell(3))).build())
              .section(ExcelCellUtils.getString(row.getCell(4)))
              .batch(ExcelCellUtils.getString(row.getCell(5)))
              .phone(ExcelCellUtils.getString(row.getCell(6)))
              .password(EncryptionUtils.encrypt(ExcelCellUtils.getDateString(row.getCell(1))))
              .build();
          student = this.saveStudent(student);
          response.setStatus(true);
          response.setId(student.getRegisterNumber());
          response.setMessage("Success");
        } catch (Exception e) {
          response.setId(student != null ? student.getRegisterNumber() : null);
          response.setMessage(e.getMessage());
        }
        responseList.add(response);
      });
      return baseResponse.successResponse(responseList);
    } catch (Exception e) {
      log.info("Error in excelRegisterStudents(): ", e);
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> excelRegisterFaculties(MultipartFile file) {
    List<Object> responseList = new ArrayList<>();
    try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
      Sheet sheet = workbook.getSheetAt(0);
      sheet.removeRow(sheet.getRow(0));                                               // remove header row
      sheet.forEach(row -> {
        var response = new RepoSaveResponseDto();
        Faculty faculty = null;
        try {
          faculty = Faculty.builder()
              .username(ExcelCellUtils.getString(row.getCell(0)))
              .password(EncryptionUtils.encrypt(ExcelCellUtils.getString(row.getCell(1))))
              .fullName(ExcelCellUtils.getString(row.getCell(2)))
              .designation(ExcelCellUtils.getString(row.getCell(3)))
              .department(Department.builder().code(ExcelCellUtils.getString(row.getCell(4))).build())
              .phone(ExcelCellUtils.getString(row.getCell(5)))
              .email(ExcelCellUtils.getString(row.getCell(6)))
              .build();
          faculty = this.saveFaculty(faculty);
          response.setStatus(true);
          response.setId(faculty.getUsername());
          response.setMessage("Success");
        } catch (Exception e) {
          response.setId(faculty != null ? faculty.getUsername() : null);
          response.setMessage(e.getMessage());
        }
        responseList.add(response);
      });
      return baseResponse.successResponse(responseList);
    } catch (Exception e) {
      log.info("Error in excelRegisterStudents(): ", e);
      return baseResponse.errorResponse(e);
    }
  }

  @Transactional
  public Student saveStudent(Student student) {
    return studentRepository.saveAndFlush(student);
  }

  @Transactional
  public Faculty saveFaculty(Faculty faculty) {
    return facultyRepository.saveAndFlush(faculty);
  }
}
