package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.constant.UserRole;
import com.rba1aji.examinationmanagementsystem.model.Configuration;
import com.rba1aji.examinationmanagementsystem.security.AllowedRoles;
import com.rba1aji.examinationmanagementsystem.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/configuration")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConfigurationController {

  private final ConfigurationService configurationService;

  @PostMapping("/v1/add-update-configuration")
  @AllowedRoles(UserRole.ADMIN)
  public ResponseEntity<?> addUpdateConfiguration(@RequestBody Configuration configuration) {
    return configurationService.addUpdateConfiguration(configuration);
  }

  @GetMapping("/v1/get-all-configuration")
  @AllowedRoles(UserRole.ADMIN)
  public ResponseEntity<?> getAllConfiguration() {
    return configurationService.getAllConfiguration();
  }

}
