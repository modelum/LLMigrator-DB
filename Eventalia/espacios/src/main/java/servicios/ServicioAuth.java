package servicios;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;

import javax.ws.rs.container.ContainerRequestContext;

public interface ServicioAuth {

  String extractToken(ContainerRequestContext requestContext);
  Claims decodeToken(String token) throws JsonProcessingException;
}
