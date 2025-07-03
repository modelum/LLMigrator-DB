package eventos.infraestructura.api.rest.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Categoría del evento")
public enum CategoriaDTO {
  @Schema(description = "Eventos de carácter académico, como conferencias y seminarios.")
  ACADEMICO,

  @Schema(description = "Eventos culturales, como exposiciones y festivales.")
  CULTURAL,

  @Schema(description = "Eventos de entretenimiento, como conciertos y espectáculos.")
  ENTRETENIMIENTO,

  @Schema(description = "Eventos deportivos, como torneos y competencias.")
  DEPORTES,

  @Schema(description = "Otras categorías no especificadas.")
  OTROS
}
