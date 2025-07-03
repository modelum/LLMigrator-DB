package arso.api.rest.auth.controller;

import arso.api.rest.auth.dto.AutorizationResponseDto;
import arso.dominio.Usuario;
import arso.servicios.ServicioAuth;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ControladorAuth {

  private final ServicioAuth servicioAuth;

  public ControladorAuth(ServicioAuth servicioAuth) {
    this.servicioAuth = servicioAuth;
  }

  // curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d
  // "username=juan&password=clave" http://localhost:8080/auth/login
  @PostMapping("/login")
  public ResponseEntity<AutorizationResponseDto> login(
      @RequestParam String username, @RequestParam String password, HttpServletResponse response) {

    Map<String, Object> claims = verificarCredenciales(username, password);

    if (claims.isEmpty()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = servicioAuth.generarToken(claims);

    Usuario usuario = servicioAuth.getUsuario(username);
    AutorizationResponseDto responseDto =
        new AutorizationResponseDto(
            usuario.getId(), usuario.getUsername(), usuario.getRoles(), token);

    response.addCookie(servicioAuth.generarCookie(token));

    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletResponse response) {

    Cookie cookie = new Cookie("jwt", "");
    cookie.setMaxAge(0);
    cookie.setHttpOnly(true);
    cookie.setPath("/");

    response.addCookie(cookie);

    return ResponseEntity.ok("Logout correcto");
  }

  private Map<String, Object> verificarCredenciales(String username, String password) {

    // Comprobar si el usuario y la contraseña son correctos
    Usuario usuario = servicioAuth.comprobarCredenciales(username, password);
    
    HashMap<String, Object> claims = new HashMap<>();

    if (usuario == null) {
      return claims;
    }

    claims.put("id", usuario.getId().toString());
    claims.put("sub", usuario.getUsername());
    claims.put("roles", String.join(",", usuario.getRoles()));

    return claims;
  }
}
