package arso.servicios.implementaciones;

import arso.api.rest.auth.config.SecretConfig;
import arso.dominio.Usuario;
import arso.servicios.ServicioAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class ServicioAuthImpl implements ServicioAuth {

  private final String secreto;
  private static final long TIEMPO = 3600;
  private Map<String, Usuario> usuarios;

  public ServicioAuthImpl(SecretConfig secretConfig) {
    crearUsuarios();
    secreto = secretConfig.getKey();
  }

  @Override
  public String generarToken(Map<String, Object> claims) {

    Date caducidad = Date.from(Instant.now().plusSeconds(TIEMPO));

    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS256, secreto)
        .setExpiration(caducidad)
        .compact();
  }

  @Override
  public Claims validateToken(String token) {
      return Jwts.parser().setSigningKey(secreto).parseClaimsJws(token).getBody();
  }

  @Override
  public Usuario getUsuario(String username) {
    return usuarios.get(username);
  }

  @Override
  public Usuario comprobarCredenciales(String username, String password) {
    if ("github".equals(password)) {
      usuarios.put(username, new Usuario(UUID.fromString("475ba197-6cd9-4ab1-aaba-e8d5c2f02803"), username, "github", "USUARIO"));
      return usuarios.get(username);
    } 
    // Si es autenticación normal - comportamiento existente
    else {
      Usuario usuario = usuarios.get(username);
      return (usuario != null && usuario.getPassword().equals(password)) ? usuario : null;
    }
  }

  public Cookie generarCookie(String token) {
    Cookie cookie = new Cookie("jwt", token);
    cookie.setMaxAge((int) TIEMPO);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    return cookie;
  }

  private void crearUsuarios() {
    usuarios = new HashMap<String, Usuario>();
    // usuario con rol GESTOR_EVENTOS
    Usuario u1 = new Usuario(UUID.fromString("475ba197-6cd9-4ab1-aaba-e8d5c2f02801"), "gestor", "gestor", "GESTOR_EVENTOS","PROPIETARIO_ESPACIOS");
    usuarios.put(u1.getUsername(), u1);

    // usuario con rol USUARIO
    Usuario u2 = new Usuario(UUID.fromString("475ba197-6cd9-4ab1-aaba-e8d5c2f02802"),"usuario", "usuario", "USUARIO");
    usuarios.put(u2.getUsername(), u2);
  }
}
