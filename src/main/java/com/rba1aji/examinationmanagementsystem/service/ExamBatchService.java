package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.dto.request.AddUpdateExamBatchReqDto;
import com.rba1aji.examinationmanagementsystem.model.Course;
import com.rba1aji.examinationmanagementsystem.model.Exam;
import com.rba1aji.examinationmanagementsystem.model.ExamBatch;
import com.rba1aji.examinationmanagementsystem.model.Faculty;
import com.rba1aji.examinationmanagementsystem.model.Marks;
import com.rba1aji.examinationmanagementsystem.model.Student;
import com.rba1aji.examinationmanagementsystem.repository.ExamBatchRepository;
import com.rba1aji.examinationmanagementsystem.repository.MarksRepository;
import com.rba1aji.examinationmanagementsystem.repository.specification.ExamBatchSpecification;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExamBatchService {

  private final BaseResponse baseResponse;
  private final ExamBatchRepository examBatchRepository;
  private final ExamBatchSpecification examBatchSpecification;
  private final StudentService studentService;
  private final MarksRepository marksRepository;

  public ResponseEntity<?> saveUpdateExamBatch(AddUpdateExamBatchReqDto dto) {
    try {
      if (!ValidationUtils.validStartTimeEndTime(dto.getStartTime(), dto.getEndTime())) {
        return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Start Time, End Time!");
      }
      long examBatchId = ValidationUtils.getLong(dto.getId());

      ExamBatch examBatch = ExamBatch.builder()
          .id(examBatchId)
          .name(dto.getName())
          .students(dto.getStudents())
          .exam(Exam.builder().id(ValidationUtils.getLong(dto.getExamId())).build())
          .course(Course.builder().id(ValidationUtils.getInt(dto.getCourseId())).build())
          .faculty(Faculty.builder().id(ValidationUtils.getInt(dto.getFacultyId())).build())
          .startTime(dto.getStartTime())
          .endTime(dto.getEndTime())
          .venue(dto.getVenue())
          .marksSubmitted(!dto.isEnableMarksEntryByAdmin())
          .active(true)
          .build();
      if (examBatchRepository.existsById(examBatchId)) {
        examBatchRepository.saveAndFlush(examBatch);
      } else {
        examBatchRepository.saveAndFlush(examBatch);
        try {
          List<Student> students = studentService.getStudentsForRegisterNumbersRange(dto.getStudents());
          List<Marks> studentMarks = students.stream().map(student ->
                  Marks.builder()
                      .student(student)
                      .attendance('-')
                      .examBatch(examBatch)
                      .exam(examBatch.getExam())
                      .course(examBatch.getCourse())
                      .build())
              .toList();
          marksRepository.saveAllAndFlush(studentMarks);
        } catch (Exception e) {
          examBatchRepository.delete(examBatch);
          e.printStackTrace();
        }
      }
      return baseResponse.successResponse(examBatch, "Successfully updated ExamBatch!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getAllExamBatchByOptionalParams(String facultyId, String examId, String courseId) {
    try {
      Specification<ExamBatch> examBatchSpec = examBatchSpecification
          .findAllByFacultyIdAndExamIdAndCourseIdAndDepartmentIdOptional(
              ValidationUtils.getLong(facultyId),
              ValidationUtils.getLong(examId),
              ValidationUtils.getInt(courseId)
          );
      return baseResponse.successResponse(examBatchRepository.findAll(examBatchSpec));
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getExamBatchById(String examBatchId) {
    try {
      long id = ValidationUtils.getLong(examBatchId);
      return baseResponse.successResponse(examBatchRepository.findById(id).orElse(null));
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> submitMarksEntryByFaculty(String examBatchIdStr) {
    try {
      long examBatchId = ValidationUtils.getLong(examBatchIdStr);
      ExamBatch examBatch = examBatchRepository.findById(examBatchId).orElse(null);
      if (examBatch != null) {
        examBatch.setMarksSubmitted(true);
        examBatchRepository.saveAndFlush(examBatch);
        return baseResponse.successResponse(examBatch, "Marks entry submitted successfully!");
      } else {
        return baseResponse.errorResponse(HttpStatus.NOT_FOUND, "ExamBatch Not found!");
      }
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
