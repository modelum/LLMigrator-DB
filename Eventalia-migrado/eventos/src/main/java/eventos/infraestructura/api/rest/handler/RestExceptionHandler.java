package eventos.infraestructura.api.rest.handler;

import eventos.infraestructura.api.rest.handler.errorDto.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class RestExceptionHandler {

  protected ErrorDto createErrorDto(String status, String titulo, String mensaje) {
    return new ErrorDto(status, titulo, mensaje);
  }

  protected ResponseEntity<Object> createResponseEntity(int status, ErrorDto error) {
    return ResponseEntity.status(status)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
        .body(error);
  }
}
