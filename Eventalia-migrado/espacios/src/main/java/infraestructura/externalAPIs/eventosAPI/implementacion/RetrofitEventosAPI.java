package infraestructura.externalAPIs.eventosAPI.implementacion;

import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitEventosAPI {
  @GET("/api/eventos/espaciosLibres")
  Call<List<UUID>> getEspaciosSinEventosYCapacidadSuficiente(
      @Query("capacidad") int capacidad,
      @Query("fechaInicio") String fechaInicio,
      @Query("fechaFin") String fechaFin,
      @Header("Authorization") String authorizationHeader);

  @GET("/api/eventos/{id}/ocupacion")
  Call<Boolean> isOcupacionActiva(
      @Path("id") UUID id, @Header("Authorization") String authorizationHeader);

  @GET("/api/eventos/ocupaciones/espacios/{idEspacio}/capacidad")
  Call<Boolean> validarNuevaCapacidadEspacio(
      @Path("idEspacio") UUID idEspacio,
      @Query("nuevaCapacidad") int nuevaCapacidad,
      @Header("Authorization") String authorizationHeader);
}
