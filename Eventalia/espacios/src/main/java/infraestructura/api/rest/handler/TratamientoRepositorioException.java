package infraestructura.api.rest.handler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import infraestructura.repositorio.excepciones.RepositorioException;

@Provider
public class TratamientoRepositorioException implements ExceptionMapper<RepositorioException> {
	@Override
	public Response toResponse(RepositorioException arg0) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(arg0.getMessage()).build();
	}
}
