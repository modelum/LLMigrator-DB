package arso.servicios;

import java.util.Map;

import javax.servlet.http.Cookie;

import arso.dominio.Usuario;
import io.jsonwebtoken.Claims;

public interface ServicioAuth {

  String generarToken(Map<String, Object> claims);

  Claims validateToken(String token);

  Usuario getUsuario(String username);

  Usuario comprobarCredenciales(String username, String password);

  Cookie generarCookie(String token);
}
