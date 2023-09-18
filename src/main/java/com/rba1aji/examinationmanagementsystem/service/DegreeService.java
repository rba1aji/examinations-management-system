package com.rba1aji.examinationmanagementsystem.service;

import com.rba1aji.examinationmanagementsystem.dto.request.DegreeSaveDto;
import com.rba1aji.examinationmanagementsystem.model.Degree;
import com.rba1aji.examinationmanagementsystem.repository.DegreeRepository;
import com.rba1aji.examinationmanagementsystem.utilities.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DegreeService {

  private final BaseResponse     baseResponse;
  private final DegreeRepository degreeRepository;

  public ResponseEntity<?> saveUpdateDegreeList(List<DegreeSaveDto> dtoList) {
    try {
      List<Degree> degreeList = dtoList.stream().map(dto -> Degree.builder()
          .code(dto.getCode())
          .name(dto.getName())
          .build()).toList();
      degreeList = degreeRepository.saveAllAndFlush(degreeList);
      return baseResponse.successResponse(degreeList);
    } catch (Exception e) {
      log.error("Error in saveUpdateDegreeList(): ", e);
      return baseResponse.errorResponse(e);
    }
  }
}
