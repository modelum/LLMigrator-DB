package reservas.infraestructura.api.rest.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(
    description =
        "DTO utilizado para crear una nueva reserva. Contiene la información necesaria para realizar la reserva de un evento.")
public class CrearReservaDto {

  @Schema(
      description = "Identificador único del evento para el cual se está creando la reserva.",
      example = "123e4567-e89b-12d3-a456-426614174000")
  @NotNull(message = "El id del evento no puede ser nulo")
  @NotEmpty(message = "El id del evento no puede estar vacío")
  @Pattern(
      regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
      message = "El ID del evento debe ser un UUID válido")
  private String idEvento;

  @Schema(
      description = "Identificador único del usuario que realiza la reserva.",
      example = "987e6543-b21a-34c7-d890-56781234abcd")
  @NotNull(message = "El id del usuario no puede ser nulo")
  @NotEmpty(message = "El id del usuario no puede estar vacío")
  @Pattern(
      regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
      message = "El ID del usuario debe ser un UUID válido")
  private String idUsuario;

  @Schema(
      description = "Número de plazas a reservar para el evento. Debe ser un valor mayor que 0.",
      example = "4")
  @DecimalMin(value = "1", message = "El número de plazas a reservar debe ser mayor que 0")
  private int plazasReservadas;

  public String getIdEvento() {
    return idEvento;
  }

  public void setIdEvento(String idEvento) {
    this.idEvento = idEvento;
  }

  public String getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(String idUsuario) {
    this.idUsuario = idUsuario;
  }

  public int getPlazasReservadas() {
    return plazasReservadas;
  }

  public void setPlazasReservadas(int plazasReservadas) {
    this.plazasReservadas = plazasReservadas;
  }
}
