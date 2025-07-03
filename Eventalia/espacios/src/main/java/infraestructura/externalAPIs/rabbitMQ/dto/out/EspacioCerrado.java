package infraestructura.externalAPIs.rabbitMQ.dto.out;

public class EspacioCerrado extends EventoRabbit {
  public EspacioCerrado() {
    super(TipoEvento.ESPACIO_CANCELADO, null);
  }

  public EspacioCerrado(String EntidadId) {
    super(TipoEvento.ESPACIO_CANCELADO, EntidadId);
  }
}
