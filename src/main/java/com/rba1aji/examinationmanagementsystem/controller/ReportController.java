package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.constant.UserRole;
import com.rba1aji.examinationmanagementsystem.dto.ExamMarksReportReqDto;
import com.rba1aji.examinationmanagementsystem.security.AllowedRoles;
import com.rba1aji.examinationmanagementsystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/exam-marks-report")
    @AllowedRoles(UserRole.ADMIN)
    public ResponseEntity<?> generateExamMarksReport(@RequestBody ExamMarksReportReqDto reqDto) {
        return reportService.generateExamMarksReport(reqDto);
    }

}
