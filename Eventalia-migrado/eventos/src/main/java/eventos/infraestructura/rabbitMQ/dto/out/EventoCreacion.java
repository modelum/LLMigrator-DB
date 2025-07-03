package eventos.infraestructura.rabbitMQ.dto.out;

import eventos.infraestructura.rabbitMQ.dto.EventoRabbit;
import eventos.infraestructura.rabbitMQ.dto.TipoEvento;

public class EventoCreacion extends EventoRabbit {
  private String nombre;
  private String descripcion;
  private String organizador;
  private String fechaInicio;
  private String fechaFin;
  private boolean cancelado;
  private int plazas;
  private String idEspacioFisico;
  private String categoria;

  public EventoCreacion() {
    super(TipoEvento.EVENTO_CREADO, null);
  }

  public EventoCreacion(
      String EntidadId,
      String nombre,
      String descripcion,
      String organizador,
      String fechaInicio,
      String fechaFin,
      int plazas,
      String idEspacioFisico,
      String categoria,
      boolean cancelado) {
    super(TipoEvento.EVENTO_CREADO, EntidadId);
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.organizador = organizador;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.plazas = plazas;
    this.idEspacioFisico = idEspacioFisico;
    this.categoria = categoria;
    this.cancelado = cancelado;
  }

  public String getNombre() {
    return nombre;
  }

  public boolean isCancelado() {
    return cancelado;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getOrganizador() {
    return organizador;
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

  public String getCategoria() {
    return categoria;
  }
}
