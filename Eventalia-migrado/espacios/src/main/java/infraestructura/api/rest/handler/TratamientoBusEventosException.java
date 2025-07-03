package infraestructura.api.rest.handler;

import infraestructura.externalAPIs.rabbitMQ.excepciones.BusEventosException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class TratamientoBusEventosException implements ExceptionMapper<BusEventosException> {
  @Override
  public Response toResponse(BusEventosException exception) {
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(exception.getMessage())
        .build();
  }
}
