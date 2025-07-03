package reservas.infraestructura.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String jwtToken = extractToken(request);

    if (jwtToken == null) {
      response.sendError(
          HttpServletResponse.SC_UNAUTHORIZED, "No se ha proporcionado el token JWT");
      return;
    }

    Claims claims = decodeToken(jwtToken);
    String[] roles = claims.get("roles").toString().split(",");

    // Establece el contexto de seguridad
    ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    Arrays.stream(roles).forEach(rol -> authorities.add(new SimpleGrantedAuthority(rol)));

    Authentication auth =
        roles[0].equals("MICROSERVICIO")
            ? new PreAuthenticatedAuthenticationToken(claims.getSubject(), null, authorities)
            : new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
    SecurityContextHolder.getContext().setAuthentication(auth);

    chain.doFilter(request, response);
  }

  private String extractToken(HttpServletRequest request) {
    // Busco el token en la cabecera Authorization
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring("Bearer ".length()).trim();
    }
    // Si no, busca el token en las cookies
    if (request.getCookies() != null) {
      for (Cookie cookie : request.getCookies()) {
        if ("jwt".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  private Claims decodeToken(String token) throws JsonProcessingException {
    String[] parts = token.split("\\.");
    if (parts.length < 2) throw new IllegalArgumentException("Token inválido");

    String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
    ObjectMapper mapper = new ObjectMapper();
    Map<String, Object> payloadMap = mapper.readValue(payload, Map.class);

    // Convertimos el Map en un objeto Claims compatible con el resto del código
    Claims claims = Jwts.claims();
    claims.putAll(payloadMap);
    return claims;
  }
}
