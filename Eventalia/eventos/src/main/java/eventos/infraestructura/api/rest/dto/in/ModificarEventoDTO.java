package eventos.infraestructura.api.rest.dto.in;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Pattern;

@Schema(name = "ModificarEventoDTO", description = "DTO para modificar un evento existente")
public class ModificarEventoDTO {

  @Schema(description = "Descripción del evento", example = "Nuevo concierto en la sala principal")
  private String descripcion;

  @Pattern(
      regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}",
      message = "Formato de fecha de inicio no válida")
  @Schema(description = "Fecha de inicio del evento", example = "2025-06-01T20:00:00")
  private String fechaInicio;

  @Pattern(
      regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}",
      message = "Formato de fecha de fin no válida")
  @Schema(description = "Fecha de fin del evento", example = "2025-06-01T22:00:00")
  private String fechaFin;

  @Schema(description = "Número de plazas disponibles", example = "200")
  private int plazas;

  @Schema(
      description = "Identificador del espacio físico donde se realizará el evento",
      example = "550e8400-e29b-41d4-a716-446655440000")
  private UUID idEspacioFisico;

  public ModificarEventoDTO(
      String descripcion,
      String organizador,
      String fechaInicio,
      String fechaFin,
      int plazas,
      UUID idEspacioFisico) {
    this.descripcion = descripcion;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.plazas = plazas;
    this.idEspacioFisico = idEspacioFisico;
  }

  public ModificarEventoDTO() {}

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(String fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public String getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(String fechaFin) {
    this.fechaFin = fechaFin;
  }

  public int getPlazas() {
    return plazas;
  }

  public void setPlazas(int plazas) {
    this.plazas = plazas;
  }

  public UUID getIdEspacioFisico() {
    return idEspacioFisico;
  }

  public void setIdEspacioFisico(UUID idEspacioFisico) {
    this.idEspacioFisico = idEspacioFisico;
  }
}
