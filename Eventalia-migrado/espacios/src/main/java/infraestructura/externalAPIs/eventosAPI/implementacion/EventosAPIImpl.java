package infraestructura.externalAPIs.eventosAPI.implementacion;

import infraestructura.externalAPIs.eventosAPI.EventosAPI;
import infraestructura.repositorio.excepciones.EntidadNoEncontrada;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventosAPIImpl implements EventosAPI {
  private final RetrofitEventosAPI eventosAPI;
  private final String bearerToken;

  public EventosAPIImpl() {
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(System.getenv("EVENTOS_API"))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    eventosAPI = retrofit.create(RetrofitEventosAPI.class);
    this.bearerToken = generateBearerToken(System.getenv("SECRET_KEY")); // Sustituir por un System.getenv()
  }

  @Override
  public List<UUID> getEspaciosSinEventosYCapacidadSuficiente(
      int capacidad, String fechaInicio, String fechaFin) throws IOException {
    Call<List<UUID>> call =
        eventosAPI.getEspaciosSinEventosYCapacidadSuficiente(
            capacidad, fechaInicio, fechaFin, bearerToken);
    Response<List<UUID>> response = call.execute();
    return response.isSuccessful() ? response.body() : List.of();
  }

  @Override
  public boolean isOcupacionActiva(UUID id) throws IOException, EntidadNoEncontrada {
    Call<Boolean> call = eventosAPI.isOcupacionActiva(id, bearerToken);
    Response<Boolean> response = call.execute();
    if (response.code() == 404) {
      throw new EntidadNoEncontrada("No se ha encontrado el espacio con id: " + id + " en eventos");
    }
    return !response.isSuccessful() || Boolean.TRUE.equals(response.body());
  }

  @Override
  public boolean validarNuevaCapacidadEspacio(UUID idEspacio, int nuevaCapacidad)
      throws IOException, EntidadNoEncontrada{
    Call<Boolean> call =
        eventosAPI.validarNuevaCapacidadEspacio(idEspacio, nuevaCapacidad, bearerToken);
    Response<Boolean> response = call.execute();
    if (response.code() == 404) {
      throw new EntidadNoEncontrada(
          "No se ha encontrado el espacio con id: " + idEspacio + " en eventos");
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
