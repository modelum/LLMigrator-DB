package reservas.infraestructura.rabbitMQ.mapper;

import reservas.dominio.Reserva;
import reservas.infraestructura.rabbitMQ.dto.out.ReservaCreacion;
import reservas.infraestructura.rabbitMQ.dto.out.ReservaEventoRabbit;

public class ReservaRabbitMapper {
  public static ReservaEventoRabbit toReservaCreada(Reserva reserva) {
    return new ReservaCreacion(
        reserva.getId().toString(),
        reserva.getIdUsuario().toString(),
        reserva.getPlazasReservadas(),
        reserva.getEvento().getId().toString());
  }
}
