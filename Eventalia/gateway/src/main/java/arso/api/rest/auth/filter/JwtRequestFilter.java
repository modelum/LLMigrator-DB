package arso.api.rest.auth.filter;

import arso.servicios.ServicioAuth;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final ServicioAuth servicioAuth;

  public JwtRequestFilter(ServicioAuth servicioAuth) {
    this.servicioAuth = servicioAuth;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return path.startsWith("/auth/login") || path.startsWith("/auth/logout") || path.startsWith("/login/oauth2/code/github");
  }

  // curl -X GET -H "Authorization: Bearer %token_jwt%"
  // http://localhost:8080/api/espacios/1
  // curl -i http://localhost:8080/api/espacios/1
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain chain)
      throws IOException, ServletException {

    String jwtToken = extractToken(request);

    if (jwtToken == null) {
      response.sendError(
          HttpServletResponse.SC_UNAUTHORIZED, "No se ha proporcionado el token JWT");
      return;
    }
    try {
      Claims claims = servicioAuth.validateToken(jwtToken);
      String[] roles = claims.get("roles", String.class).split(",");

      // Establece el contexto de seguridad
      ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      Arrays.stream(roles).forEach(rol -> authorities.add(new SimpleGrantedAuthority(rol)));

      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
      // Establecemos la autenticación en el contexto de seguridad
      // Se interpreta como que el usuario ha superado la autenticación
      SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (Exception e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "El token JWT no es correcto");
      return;
    }

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
}
