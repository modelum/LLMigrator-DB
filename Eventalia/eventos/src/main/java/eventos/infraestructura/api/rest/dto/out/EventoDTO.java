package eventos.infraestructura.api.rest.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "EventoDto", description = "Evento de la aplicación")
public class EventoDTO {

  @Schema(
      description = "Identificador del evento",
      example = "123e4567-e89b-12d3-a456-426614174000",
      required = true)
  private UUID id;

  @Schema(description = "Nombre del evento", example = "Concierto de rock", required = true)
  private String nombre;

  @Schema(
      description = "Descripción del evento",
      example = "Concierto de rock en la sala de conciertos de la ciudad",
      required = true)
  private String descripcion;

  @Schema(
      description = "Organizador del evento",
      example = "Ayuntamiento de la ciudad",
      required = true)
  private String organizador;

  @Schema(description = "Número de plazas del evento", example = "100", required = true)
  private int numPlazas;

  @Schema(description = "Indica si el evento ha sido cancelado", example = "false", required = true)
  private boolean cancelado;

  @Schema(description = "Categoría del evento", example = "Concierto", required = true)
  private String categoria;

  @Schema(
      description = "Ocupación del evento",
      implementation = OcupacionDTO.class,
      required = true)
  private OcupacionDTO ocupacion;

  public EventoDTO() {}

  // Constructor para eventos no cancelados
  public EventoDTO(
      UUID id,
      String nombre,
      String descripcion,
      String organizador,
      int numPlazas,
      boolean cancelado,
      String categoria,
      LocalDateTime fechaInicio,
      LocalDateTime fechaFin,
      UUID idEspacioFisico,
      String nombreEspacioFisico,
      String direccionEspacioFisico) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.organizador = organizador;
    this.numPlazas = numPlazas;
    this.cancelado = cancelado;
    this.categoria = categoria;
    this.ocupacion =
        new OcupacionDTO(
            fechaInicio, fechaFin, idEspacioFisico, nombreEspacioFisico, direccionEspacioFisico);
  }

  // Constructor para eventos cancelados
  public EventoDTO(
      UUID id,
      String nombre,
      String descripcion,
      String organizador,
      int numPlazas,
      boolean cancelado,
      String categoria) {
    this.id = id;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.organizador = organizador;
    this.numPlazas = numPlazas;
    this.cancelado = cancelado;
    this.categoria = categoria;
    this.ocupacion = null;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getOrganizador() {
    return organizador;
  }

  public void setOrganizador(String organizador) {
    this.organizador = organizador;
  }

  public int getNumPlazas() {
    return numPlazas;
  }

  public void setNumPlazas(int numPlazas) {
    this.numPlazas = numPlazas;
  }

  public boolean isCancelado() {
    return cancelado;
  }

  public void setCancelado(boolean cancelado) {
    this.cancelado = cancelado;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public OcupacionDTO getOcupacion() {
    return ocupacion;
  }

  public void setOcupacion(OcupacionDTO ocupacion) {
    this.ocupacion = ocupacion;
  }
}
