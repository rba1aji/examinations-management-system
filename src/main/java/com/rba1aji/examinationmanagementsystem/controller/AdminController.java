package com.rba1aji.examinationmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
  //ToDo: Access levels to users, permit, disable

  @GetMapping
  public String home() {
    return "IM ALIVE";
  }

}
