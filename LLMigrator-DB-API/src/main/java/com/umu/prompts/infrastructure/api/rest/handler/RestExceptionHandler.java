package com.umu.prompts.infrastructure.api.rest.handler;

import com.umu.prompts.infrastructure.api.rest.dto.error.ProblemDetail;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<Object> handleIllegalArgumentException(
      IllegalArgumentException ex, WebRequest request) {
    return createResponseEntity(
        HttpStatus.BAD_REQUEST.value(),
        createProblemDetail(
            HttpStatus.BAD_REQUEST.value(),
            "Illegal Argument",
            ex.getMessage(),
            request.getDescription(false)));
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
    return createResponseEntity(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        createProblemDetail(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            ex.getMessage(),
            request.getDescription(false)));
  }

  @Override
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  private ProblemDetail createProblemDetail(
      Integer status, String title, String detail, String instance) {
    ProblemDetail problemDetail = new ProblemDetail();
    problemDetail.setTitle(title);
    problemDetail.setStatus(status);
    problemDetail.setDetail(detail);
    problemDetail.setInstance(instance);
    return problemDetail;
  }

  private ResponseEntity<Object> createResponseEntity(Integer status, ProblemDetail errorDto) {
    return ResponseEntity.status(status)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
        .body(errorDto);
  }
}
