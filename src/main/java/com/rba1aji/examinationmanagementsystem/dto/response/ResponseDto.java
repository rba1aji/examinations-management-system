package com.rba1aji.examinationmanagementsystem.dto.response;

import lombok.Data;

@Data
public class ResponseDto {
  private boolean status;
  private Object data;
  private String message;
}
