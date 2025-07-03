package eventos.infraestructura.rabbitMQ.dto.in;

import eventos.infraestructura.rabbitMQ.dto.EventoRabbit;
import eventos.infraestructura.rabbitMQ.dto.TipoEvento;

public class EspacioCerrado extends EventoRabbit {
  public EspacioCerrado() {
    super(TipoEvento.ESPACIO_CANCELADO, null);
  }

  public EspacioCerrado(String EntidadId) {
    super(TipoEvento.ESPACIO_CANCELADO, EntidadId);
  }
}
