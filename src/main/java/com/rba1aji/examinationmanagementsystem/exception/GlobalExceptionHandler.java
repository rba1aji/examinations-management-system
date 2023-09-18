package com.rba1aji.examinationmanagementsystem.exception;

import com.rba1aji.examinationmanagementsystem.dto.response.ResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(UnexpectedRollbackException.class)
  public ResponseDto handleJpaUnexpectedRollbackException(UnexpectedRollbackException ex) {
    ResponseDto responseDto = new ResponseDto();
    responseDto.setMessage("Invalid payload error.");
    Map<String, String> errors = new HashMap<>();
    errors.put("reason", ex.getLocalizedMessage());
    responseDto.setData(errors);
    return responseDto;
  }

  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseDto handleConstraintViolationException(ConstraintViolationException ex) {
    ResponseDto responseDto = new ResponseDto();
    responseDto.setMessage("Duplicate Entry Error.");
    Map<String, String> errors = new HashMap<>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.put(violation.getRootBeanClass().getName(), violation.getMessage());
    }
    responseDto.setData(errors);
    return responseDto;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseDto handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    ResponseDto responseDto = new ResponseDto();
    responseDto.setMessage("Duplicate Entry Error.");
    responseDto.setData(ex.getMessage());
    return responseDto;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseDto handleValidationExceptions(MethodArgumentNotValidException ex) {
    ResponseDto responseDto = new ResponseDto();
    responseDto.setMessage("Validation Error.");
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    responseDto.setData(errors);
    return responseDto;
  }
}
