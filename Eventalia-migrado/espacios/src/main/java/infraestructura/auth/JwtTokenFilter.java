package infraestructura.auth;

import io.jsonwebtoken.Claims;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Priority;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import servicios.ServicioAuth;
import servicios.factoria.FactoriaServicios;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtTokenFilter implements ContainerRequestFilter {

  @Context private ResourceInfo resourceInfo;

  private ServicioAuth servicioAuth = FactoriaServicios.getServicio(ServicioAuth.class);

  // curl -X GET -H "Authorization: Bearer %token_jwt%" http://localhost:8080/api/espacios/1
  // curl -i http://localhost:8080/api/espacios/1
  @Override
  public void filter(ContainerRequestContext requestContext) {

    String token = servicioAuth.extractToken(requestContext);
    if (token == null) {
      requestContext.abortWith(
          Response.status(Response.Status.UNAUTHORIZED)
              .entity("No se adjunta el token correctamente")
              .build());
      return;
    }

    try {
      Claims claims = servicioAuth.decodeToken(token);

      Set<String> roles =
          new HashSet<>(Arrays.asList(claims.get("roles", String.class).trim().split(",")));

      if (this.resourceInfo.getResourceMethod().isAnnotationPresent(RolesAllowed.class)) {
        String[] allowedRoles =
            resourceInfo.getResourceMethod().getAnnotation(RolesAllowed.class).value();

        if (roles.stream().noneMatch(userRole -> Arrays.asList(allowedRoles).contains(userRole))) {
          requestContext.abortWith(
              Response.status(Response.Status.FORBIDDEN).entity("no tiene rol de acceso").build());
        }
      }
    } catch (Exception e) { // Error de validación
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }
}
