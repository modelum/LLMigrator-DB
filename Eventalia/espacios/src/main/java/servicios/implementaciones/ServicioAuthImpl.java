package servicios.implementaciones;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Base64;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import servicios.ServicioAuth;

public class ServicioAuthImpl implements ServicioAuth {

  @Override
  public String extractToken(ContainerRequestContext requestContext) {
    String authHeader = requestContext.getHeaderString("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring("Bearer ".length()).trim();
    }
    return requestContext.getCookies().values().stream()
        .filter(cookie -> "jwt".equals(cookie.getName()))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(null);
  }

  @Override
  public Claims decodeToken(String token) throws JsonProcessingException {
    String[] parts = token.split("\\.");
    if (parts.length < 2) throw new IllegalArgumentException("Token inválido");

    String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> payloadMap = mapper.readValue(payload, Map.class);

    Claims claims = Jwts.claims();
    claims.putAll(payloadMap);
    return claims;
  }
}
