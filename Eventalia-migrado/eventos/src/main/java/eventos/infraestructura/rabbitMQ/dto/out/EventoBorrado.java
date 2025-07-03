package eventos.infraestructura.rabbitMQ.dto.out;

import eventos.infraestructura.rabbitMQ.dto.EventoRabbit;
import eventos.infraestructura.rabbitMQ.dto.TipoEvento;

public class EventoBorrado extends EventoRabbit {
  public EventoBorrado() {
    super(TipoEvento.EVENTO_CANCELADO, null);
  }

  public EventoBorrado(String EntidadId) {
    super(TipoEvento.EVENTO_CANCELADO, EntidadId);
  }
}
