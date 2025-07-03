package reservas.infraestructura.rabbitMQ.dto.in;

public class EventoBorrado extends EventoRabbit {
  public EventoBorrado() {
    super(TipoEvento.EVENTO_CANCELADO, null);
  }

  public EventoBorrado(String EntidadId) {
    super(TipoEvento.EVENTO_CANCELADO, EntidadId);
  }
}
