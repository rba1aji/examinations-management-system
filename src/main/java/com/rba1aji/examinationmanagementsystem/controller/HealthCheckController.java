package com.rba1aji.examinationmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HealthCheckController {

  @GetMapping
  public String healthCheck() {
    return "I AM ALIVE";
  }

}
