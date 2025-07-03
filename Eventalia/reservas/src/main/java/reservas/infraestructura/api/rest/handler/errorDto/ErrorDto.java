package reservas.infraestructura.api.rest.handler.errorDto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description =
        "DTO utilizado para representar un error en la API. Contiene información sobre el estado, título y mensaje del error.")
public final class ErrorDto {

  @Schema(
      description =
          "El estado del error, usualmente un código de estado HTTP o una descripción breve.",
      example = "400")
  private final String estado;

  @Schema(
      description = "Título corto que describe el tipo de error ocurrido.",
      example = "Solicitud inválida")
  private final String titulo;

  @Schema(
      description =
          "Mensaje detallado que explica la causa del error, habitualmente para el desarrollador frontend.",
      example = "El número de plazas a reservar debe ser mayor que 0")
  private final String mensaje;

  @Schema(
      description =
          "Nombre del microservicio que ha generado el error, útil para identificar el origen del problema.",
      example = "reservas")
  private final String microservicio;

  public ErrorDto(String estado, String titulo, String mensaje) {
    this.estado = estado;
    this.titulo = titulo;
    this.mensaje = mensaje;
    this.microservicio = "/reservas";
  }

  public String getEstado() {
    return estado;
  }

  public String getMensaje() {
    return mensaje;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getMicroservicio() {
    return microservicio;
  }
}
