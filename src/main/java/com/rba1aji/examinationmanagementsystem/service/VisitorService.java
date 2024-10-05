package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.model.Visitor;
import com.rba1aji.examinationmanagementsystem.repository.VisitorRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class VisitorService {

  private final VisitorRepository visitorRepository;
  private final BaseResponse      baseResponse;

  public ResponseEntity<?> saveVisitor(Visitor visitor) {
    try {
      visitorRepository.save(visitor);
      return baseResponse.successResponse("Visitor saved successfully");
    } catch (Exception e) {
      return baseResponse.errorResponse(e);
    }
  }

}
