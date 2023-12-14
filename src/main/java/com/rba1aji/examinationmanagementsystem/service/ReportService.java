package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.ExamMarksReportEnum;
import com.rba1aji.examinationmanagementsystem.dto.ExamMarksReportReqDto;
import com.rba1aji.examinationmanagementsystem.model.Marks;
import com.rba1aji.examinationmanagementsystem.repository.ExamRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import com.rba1aji.examinationmanagementsystem.utilities.ExcelFileGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportService {

  private final MarksService marksService;
  private final ExamRepository examRepository;
  private final BaseResponse baseResponse;

  public ResponseEntity<?> generateExamMarksReport(ExamMarksReportReqDto reqDto) {
    try {
      if (reqDto.getExamId() == 0 || !examRepository.existsById(reqDto.getExamId())) {
        return baseResponse.errorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid exam id!");
      }
      ExcelFileGenerator file = new ExcelFileGenerator("exam_marks");
      List<Marks> marksList = marksService.getAllMarksForExamMarksReport(reqDto);
      file.addRow();
      for (ExamMarksReportEnum en : ExamMarksReportEnum.values()) {
        file.addCell(en.getHeader());
      }
      marksList.forEach(marks -> {
        file.addRow();
        for (ExamMarksReportEnum en : ExamMarksReportEnum.values()) {
          Object value = null;
          try {
            value = en.getValue(marks);
          } catch (Exception e) {
            e.printStackTrace();
          }
          file.addCell(value);
        }
      });

      byte[] fileBytes = file.exportFileAsBytes();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
      headers.setContentDispositionFormData("attachment", "exam_marks_report_" + new Date().getTime() + ".xlsx");

      return ResponseEntity.ok()
          .headers(headers)
          .body(fileBytes);
    } catch (Exception e) {
      e.printStackTrace();
      return baseResponse.errorResponse(e);
    }
  }

}
