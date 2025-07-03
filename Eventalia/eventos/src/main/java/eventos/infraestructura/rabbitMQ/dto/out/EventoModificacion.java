package eventos.infraestructura.rabbitMQ.dto.out;

import eventos.infraestructura.rabbitMQ.dto.EventoRabbit;
import eventos.infraestructura.rabbitMQ.dto.TipoEvento;

public class EventoModificacion extends EventoRabbit {
  private String descripcion;
  private String fechaInicio;
  private  String fechaFin;
  private int plazas;
  private String idEspacioFisico;

  public EventoModificacion() {
    super(TipoEvento.EVENTO_MODIFICADO, null);
  }

  public EventoModificacion(
      String entidadId,
      String descripcion,
      String fechaInicio,
      String fechaFin,
      int plazas,
      String idEspacioFisico) {
    super(TipoEvento.EVENTO_MODIFICADO, entidadId);
    this.descripcion = descripcion;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.plazas = plazas;
    this.idEspacioFisico = idEspacioFisico;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getFechaInicio() {
    return fechaInicio;
  }

  public String getFechaFin() {
    return fechaFin;
  }

  public int getPlazas() {
    return plazas;
  }

  public String getIdEspacioFisico() {
    return idEspacioFisico;
  }
}
