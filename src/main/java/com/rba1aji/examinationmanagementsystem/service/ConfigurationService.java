package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.Configuration;
import com.rba1aji.examinationmanagementsystem.repository.ConfigurationRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConfigurationService {

  private final ConfigurationRepository configurationRepository;
  private final BaseResponse baseResponse;

  public ResponseEntity<?> addUpdateConfiguration(Configuration configuration) {
    try {
      configuration.setActive(true);
      configurationRepository.saveAndFlush(configuration);
      return baseResponse.successResponse(configuration, "Configuration added/updated successfully!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

  public ResponseEntity<?> getAllConfiguration() {
    try {
      return baseResponse.successResponse(configurationRepository.findAllByActive(true), "Fetched all configuration successfully!");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
