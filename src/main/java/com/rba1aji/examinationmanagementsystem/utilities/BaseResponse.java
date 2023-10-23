package com.rba1aji.examinationmanagementsystem.utilities;

import com.rba1aji.examinationmanagementsystem.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseResponse {
  public ResponseEntity<ResponseDto> successResponse(Object data) {
    var res = new ResponseDto();
    res.setStatus(true);
    res.setData(data);
    res.setMessage("Success");
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  public ResponseEntity<ResponseDto> successResponse(String message) {
    var res = new ResponseDto();
    res.setStatus(true);
    res.setMessage(message);
    return new ResponseEntity<>(res, HttpStatus.OK);
  }

  public ResponseEntity<ResponseDto> errorResponse(HttpStatus httpStatus, String message) {
    var res = new ResponseDto();
    res.setMessage(message);
    return new ResponseEntity<>(res, httpStatus);
  }

  public ResponseEntity<ResponseDto> errorResponse(Exception e) {
    var res = new ResponseDto();
    res.setMessage(e.getMessage());
    return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
