package arso.api.rest.auth.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import arso.api.rest.auth.dto.AutorizationResponseDto;
import arso.servicios.ServicioAuth;
import arso.dominio.Usuario;

@Component
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {

  private static final int JWT_TIEMPO_VALIDEZ = 3600;

  private final ServicioAuth servicioAuth;

  private final ObjectMapper objectMapper;

  public SecuritySuccessHandler(ServicioAuth servicioAuth) {
    this.servicioAuth = servicioAuth;
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {

    DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

    Map<String, Object> claims = fetchUserInfo(user);

    Usuario usuario = servicioAuth.getUsuario(user.getAttributes().get("login").toString());

    if (claims != null) {
      // Generamos el token JWT
      String token = servicioAuth.generarToken(claims);

      // Construimos el DTO de respuesta
      AutorizationResponseDto responseDto =
          new AutorizationResponseDto(
              usuario.getId(), usuario.getUsername(), usuario.getRoles(), token);

      response.addCookie(servicioAuth.generarCookie(token));

      // lo pasamos como query -param (base64url-encoded)
      String json      = new ObjectMapper().writeValueAsString(responseDto);
      String base64url = Base64.getUrlEncoder().withoutPadding()
                             .encodeToString(json.getBytes(StandardCharsets.UTF_8));

    // ---------- 4) redirección ----------
      String target = "http://localhost:5173?data=" + base64url;
      response.sendRedirect(target); 
    }
  }

  private Map<String, Object> fetchUserInfo(DefaultOAuth2User user) {

    String username = user.getAttributes().get("login").toString();
    Usuario usuario = servicioAuth.comprobarCredenciales(username, "github");

    if (usuario == null) {
      return null;
    }

    HashMap<String, Object> claims = new HashMap<String, Object>();
    claims.put("id", usuario.getId().toString());
    claims.put("sub", usuario.getUsername());
    claims.put("roles", String.join(",", usuario.getRoles()));

    return claims;
  }
}
