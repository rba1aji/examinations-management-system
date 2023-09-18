package com.rba1aji.examinationmanagementsystem.dto.response;

import lombok.Data;

import java.util.Calendar;

@Data
public class ResponseDto {
  private boolean  status;
  private Object   data;
  private String   message;
  private Calendar timestamp = Calendar.getInstance();
}
