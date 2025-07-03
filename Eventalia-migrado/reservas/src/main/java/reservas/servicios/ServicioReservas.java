package reservas.servicios;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reservas.dominio.Reserva;

public interface ServicioReservas {

  /**
   * - ruta de acceso: "/api/reservas" - método: POST - respuesta: 201 CREATED - body: idReserva o
   * ReservaDTO + cabecera location con la URI de la reserva creada
   */
  UUID reservar(UUID idEvento, UUID idUsuario, int plazasReservadas) throws Exception;

  /**
   * - ruta de acceso: "/api/reservas/{idReserva}" - método: GET - respuesta: 200 OK - body:
   * ReservaDTO
   */
  Reserva get(UUID idReserva) throws Exception;

  /**
   * - ruta de acceso: "/api/eventos/{idEvento}/reservas" - método: GET - respuesta: 200 OK - body:
   * List<ReservaDTO>
   */
  Page<Reserva> getAll(UUID idEvento, Pageable pageable) throws Exception;

  List<Reserva> getAll(UUID idUsuario) throws Exception;

  void cancelar(UUID idReserva) throws Exception;

  boolean validarNuevasPlazasEvento(UUID idEvento, int plazas);
}
