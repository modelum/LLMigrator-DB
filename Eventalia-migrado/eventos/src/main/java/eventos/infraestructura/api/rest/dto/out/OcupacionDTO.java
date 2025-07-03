package eventos.infraestructura.api.rest.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "OcupacionDto", description = "Ocupación de un espacio físico en un evento")
public class OcupacionDTO {
  @Schema(
      description = "Fecha y hora de inicio de la ocupación",
      example = "2023-10-01T10:00:00",
      required = true)
  private LocalDateTime fechaInicio;

  @Schema(
      description = "Fecha y hora de fin de la ocupación",
      example = "2023-10-01T12:00:00",
      required = true)
  private LocalDateTime fechaFin;

  @Schema(
      description = "Identificador del espacio físico",
      example = "123e4567-e89b-12d3-a456-426614174000",
      required = true)
  private UUID idEspacioFisico;

  @Schema(
      description = "Nombre del espacio físico donde se celebra el evento",
      example = "Sala de conferencias",
      required = true)
  private String nombreEspacioFisico;

  @Schema(
      description = "Dirección del espacio físico donde se celebra el evento",
      example = "Calle Mayor, 123, Madrid",
      required = true)
  private String direccionEspacioFisico;

  public OcupacionDTO() {}

  public OcupacionDTO(
      LocalDateTime fechaInicio,
      LocalDateTime fechaFin,
      UUID idEspacioFisico,
      String nombreEspacioFisico,
      String direccionEspacioFisico) {
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.idEspacioFisico = idEspacioFisico;
    this.nombreEspacioFisico = nombreEspacioFisico;
    this.direccionEspacioFisico = direccionEspacioFisico;
  }

  public LocalDateTime getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(LocalDateTime fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public LocalDateTime getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(LocalDateTime fechaFin) {
    this.fechaFin = fechaFin;
  }

  public UUID getIdEspacioFisico() {
    return idEspacioFisico;
  }

  public void setIdEspacioFisico(UUID idEspacioFisico) {
    this.idEspacioFisico = idEspacioFisico;
  }

  public String getDireccionEspacioFisico() {
    return direccionEspacioFisico;
  }

  public void setDireccionEspacioFisico(String direccionEspacioFisico) {
    this.direccionEspacioFisico = direccionEspacioFisico;
  }

  public String getNombreEspacioFisico() {
    return nombreEspacioFisico;
  }

  public void setNombreEspacioFisico(String nombreEspacioFisico) {
    this.nombreEspacioFisico = nombreEspacioFisico;
  }
}
