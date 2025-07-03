package eventos.infraestructura.rabbitMQ.mapper;

import eventos.dominio.Evento;
import eventos.infraestructura.rabbitMQ.dto.EventoRabbit;
import eventos.infraestructura.rabbitMQ.dto.out.*;

public class EventoRabbitMapper {
  public static EventoRabbit toEventoCreacion(Evento evento) {
    return new EventoCreacion(
        evento.getId().toString(),
        evento.getNombre(),
        evento.getDescripcion(),
        evento.getOrganizador(),
        evento.getFechaInicio().toString(),
        evento.getFechaFin().toString(),
        evento.getPlazas(),
        evento.getEspacioFisico().getId().toString(),
        evento.getCategoria().toString(),
        evento.isCancelado());
  }

  public static EventoRabbit toEventoModificacion(Evento evento) {
    return new EventoModificacion(
        evento.getId().toString(),
        evento.getDescripcion(),
        evento.getFechaInicio().toString(),
        evento.getFechaFin().toString(),
        evento.getPlazas(),
        evento.getEspacioFisico().getId().toString());
  }

  public static EventoRabbit toEventoBorrado(String entidadId) {
    return new EventoBorrado(entidadId);
  }
}
