package eventos.infraestructura.api.rest.dto.in;

import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CrearEventoDto", description = "DTO para la cración de un evento")
public class CrearEventoDto {

  @NotEmpty(message = "El nombre no puede estar vacío")
  @Schema(description = "Nombre del evento", example = "Concierto de rock", required = true)
  private String nombre;

  @Schema(
      description = "Descripción del evento",
      example = "Concierto de rock en el parque",
      required = true)
  private String descripcion;

  @NotEmpty(message = "El organizador no puede estar vacío")
  @Schema(
      description = "Organizador del evento",
      example = "Ayuntamiento de Madrid",
      required = true)
  private String organizador;

  @NotNull(message = "La categoría no puede estar vacía")
  @Schema(description = "Categoría del evento", example = "ENTRETENIMIENTO", required = true)
  private CategoriaDTO categoria;

  @NotNull
  @Pattern(
      regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}",
      message = "Formato de fecha de inicio no válida")
  @Schema(
      description = "Fecha de inicio del evento",
      example = "2021-06-01T20:00:00",
      required = true)
  private String fechaInicio;

  @NotNull
  @Pattern(
      regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}",
      message = "Formato de fecha de fin no válida")
  @Schema(description = "Fecha de fin del evento", example = "2021-06-01T22:00:00", required = true)
  private String fechaFin;

  @NotNull(message = "El número de plazas es obligatorio")
  @Min(value = 1, message = "El número de plazas debe ser al menos 1")
  @Schema(description = "Número de plazas disponibles", example = "100", required = true)
  private int plazas;

  @NotNull(message = "El identificador del espacio físico es obligatorio")
  @Schema(
      description = "Identificador del espacio físico donde se realizará el evento",
      example = "123e4567-e89b-12d3-a456-426614174000",
      required = true)
  private UUID idEspacioFisico;

  public CrearEventoDto(
      String nombre,
      String descripcion,
      String organizador,
      CategoriaDTO categoria,
      String fechaInicio,
      String fechaFin,
      int plazas,
      UUID idEspacioFisico) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.organizador = organizador;
    this.categoria = categoria;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.plazas = plazas;
    this.idEspacioFisico = idEspacioFisico;
  }

  public CrearEventoDto() {}

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

  public CategoriaDTO getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaDTO categoria) {
    this.categoria = categoria;
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
