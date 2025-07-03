package reservas.infraestructura.api.rest.mapper;

import reservas.dominio.Reserva;
import reservas.infraestructura.api.rest.dto.out.ReservaDto;

public class ReservaMapper {

  public static ReservaDto toDTO(Reserva reserva) {
    return new ReservaDto(
        reserva.getId(),
        reserva.getIdUsuario(),
        reserva.getPlazasReservadas(),
        reserva.getEvento().getId(),
        reserva.isCancelado(),
        reserva.getEvento().getFechaInicio(),
        reserva.getEvento().getNombreEvento());
  }
}
