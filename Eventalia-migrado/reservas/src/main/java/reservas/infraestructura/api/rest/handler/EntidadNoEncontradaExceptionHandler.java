package reservas.infraestructura.api.rest.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reservas.infraestructura.repositorios.excepciones.EntidadNoEncontrada;

@RestControllerAdvice
public class EntidadNoEncontradaExceptionHandler extends RestExceptionHandler {

  @ExceptionHandler(EntidadNoEncontrada.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleEntidadNoEncontradaException(EntidadNoEncontrada e) {
    return createResponseEntity(404, createErrorDto("404", "Not Found", e.getMessage()));
  }
}
