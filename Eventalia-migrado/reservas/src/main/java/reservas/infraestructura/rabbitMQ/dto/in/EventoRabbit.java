package reservas.infraestructura.rabbitMQ.dto.in;

import java.time.LocalDateTime;

public abstract class EventoRabbit {
  private TipoEvento tipoEvento;
  private String fechaCreacion;
  private String entidadId;

  public EventoRabbit() {}

  protected EventoRabbit(TipoEvento tipoEvento, String entidadId) {
    this.tipoEvento = tipoEvento;
    this.fechaCreacion = LocalDateTime.now().toString();
    this.entidadId = entidadId;
  }

  public TipoEvento getTipoEvento() {
    return tipoEvento;
  }

  public String getFechaCreacion() {
    return fechaCreacion;
  }

  public String getEntidadId() {
    return this.entidadId;
  }
}
