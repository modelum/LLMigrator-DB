package reservas.infraestructura.rabbitMQ.dto.out;

import java.time.LocalDateTime;

public abstract class ReservaEventoRabbit {
  private ReservaTipoEvento tipoEvento;
  private String fechaCreacion;
  private String EntidadId;

  public ReservaEventoRabbit() {}

  protected ReservaEventoRabbit(ReservaTipoEvento tipoEvento, String EntidadId) {
    this.tipoEvento = tipoEvento;
    this.fechaCreacion = LocalDateTime.now().toString();
    this.EntidadId = EntidadId;
  }

  public ReservaTipoEvento getTipoEvento() {
    return tipoEvento;
  }

  public String getFechaCreacion() {
    return fechaCreacion;
  }

  public String getEntidadId() {
    return EntidadId;
  }
}
