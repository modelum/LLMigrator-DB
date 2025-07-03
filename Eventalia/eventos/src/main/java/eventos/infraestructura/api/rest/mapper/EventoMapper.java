package eventos.infraestructura.api.rest.mapper;

import eventos.dominio.Evento;
import eventos.infraestructura.api.rest.dto.out.EventoDTO;

public class EventoMapper {

  public static EventoDTO toDTO(Evento evento) {

    return evento.isCancelado() && evento.getOcupacion() == null
        ? new EventoDTO(
            evento.getId(),
            evento.getNombre(),
            evento.getDescripcion(),
            evento.getOrganizador(),
            evento.getPlazas(),
            evento.isCancelado(),
            evento.getCategoria().toString())
        : new EventoDTO(
            evento.getId(),
            evento.getNombre(),
            evento.getDescripcion(),
            evento.getOrganizador(),
            evento.getPlazas(),
            evento.isCancelado(),
            evento.getCategoria().toString(),
            evento.getFechaInicio(),
            evento.getFechaFin(),
            evento.getEspacioFisico().getId(),
            evento.getEspacioFisico().getNombre(),
            evento.getEspacioFisico().getDireccion());
  }
}
