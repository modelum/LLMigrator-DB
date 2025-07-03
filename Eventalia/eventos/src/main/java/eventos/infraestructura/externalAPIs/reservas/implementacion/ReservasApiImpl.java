package eventos.infraestructura.externalAPIs.reservas.implementacion;

import eventos.infraestructura.externalAPIs.reservas.ReservasAPI;
import eventos.infraestructura.externalAPIs.reservas.RetrofitReservasAPI;
import eventos.infraestructura.externalAPIs.reservas.config.ApiConfig;
import eventos.infraestructura.repositorios.excepciones.EntidadNoEncontrada;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class ReservasApiImpl implements ReservasAPI {

  private final RetrofitReservasAPI reservasAPI;
  private final String bearerToken;

  public ReservasApiImpl(ApiConfig apiConfig) {
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(apiConfig.getReservas())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();
    reservasAPI = retrofit.create(RetrofitReservasAPI.class);

    this.bearerToken = generateBearerToken(apiConfig.getSecretoReservas());
  }

  @Override
  public boolean validarNuevasPlazasEvento(UUID idEvento, int plazas) throws IOException, EntidadNoEncontrada {
    Call<Boolean> call = reservasAPI.validarNuevasPlazasEvento(idEvento, plazas, bearerToken);
    Response<Boolean> response = call.execute();
    if(response.code() == 404){
      throw new EntidadNoEncontrada("No se ha encontrado el evento con id: " + idEvento + " en reservas");
    }
    return response.isSuccessful() && Boolean.TRUE.equals(response.body());
  }

  private String generateBearerToken(String secretoEventos) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("username", "MICROSERVICIO");
    claims.put("roles", String.join(",", "MICROSERVICIO"));

    // Lo dejamos sin expiración
    String token =
        Jwts.builder()
            .setClaims(claims)
            .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretoEventos)
            .compact();

    return "Bearer " + token;
  }
}
