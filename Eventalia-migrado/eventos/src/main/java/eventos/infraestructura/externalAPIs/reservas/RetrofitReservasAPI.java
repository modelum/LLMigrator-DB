package eventos.infraestructura.externalAPIs.reservas;

import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitReservasAPI {
  @GET("/api/eventos/{idEvento}/plazas")
  Call<Boolean> validarNuevasPlazasEvento(
      @Path("idEvento") UUID idEvento,
      @Query("plazas") int plazas,
      @Header("Authorization") String authorizationHeader);
}
