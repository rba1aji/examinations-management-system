package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.constant.UserRole;
import com.rba1aji.examinationmanagementsystem.dto.EnableDisableEvaluationBundlePrintoutReqDto;
import com.rba1aji.examinationmanagementsystem.security.AllowedRoles;
import com.rba1aji.examinationmanagementsystem.service.EvaluationBundleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/evaluation-bundle")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluationBundleController {

  private final EvaluationBundleService evaluationBundleService;

  @GetMapping("/v1/get-all-evaluation-bundle-for-evaluation")
  @AllowedRoles({UserRole.ADMIN, UserRole.FACULTY})
  public ResponseEntity<?> getAllEvaluationBundles(@RequestParam("evaluationId") int evaluationId) {
    return evaluationBundleService.getAllEvaluationBundleForEvaluationId(evaluationId);
  }

  @GetMapping("/v1/get-evaluation-bundle")
  @AllowedRoles({UserRole.ADMIN, UserRole.FACULTY})
  public ResponseEntity<?> getEvaluationBundle(@RequestParam("evaluationBundleId") int evaluationBundleId) {
    return evaluationBundleService.getEvaluationBundleForId(evaluationBundleId);
  }

  @PutMapping("/v1/enable-disable-printout-for-evaluation-bundles")
  @AllowedRoles({UserRole.ADMIN, UserRole.FACULTY})
  public ResponseEntity<?> disablePrintoutForEvaluationBundle(@RequestBody EnableDisableEvaluationBundlePrintoutReqDto reqDto) {
    return evaluationBundleService.disablePrintoutForEvaluationBundle(reqDto);
  }

}
