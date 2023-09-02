package com.security.session.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class IntegrationExceptionController {

  @ExceptionHandler(IllegalAccessException.class)
  public ResponseEntity<Object> handlerIllegalEx(Exception ex) {
    Map<HttpStatus, String> returnStatus = createReturnStatus(HttpStatus.BAD_REQUEST,
        ex.getMessage());

    return ResponseEntity.badRequest().body(returnStatus);
  }

  private Map<HttpStatus, String> createReturnStatus(HttpStatus status, String message) {
    HashMap<HttpStatus, String> returnStat = new HashMap<>();
    returnStat.put(status, message);

    return returnStat;
  }

}
