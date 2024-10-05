package com.rba1aji.examinationmanagementsystem.controller;

import com.rba1aji.examinationmanagementsystem.model.Visitor;
import com.rba1aji.examinationmanagementsystem.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor")
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class VisitorController {

  private final VisitorService visitorService;

  @PostMapping("/save-visitor")
  public ResponseEntity<?> saveVisitor(@RequestBody Visitor visitor) {
    return visitorService.saveVisitor(visitor);
  }

}
