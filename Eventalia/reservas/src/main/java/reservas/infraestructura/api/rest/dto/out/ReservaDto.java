package reservas.infraestructura.api.rest.dto.out;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO de una reserva dentro del microservicio.")
public class ReservaDto {

    @Schema(description = "Identificador único de la reserva.", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Identificador del usuario que realizó la reserva.", example = "987e6543-b21a-34c7-d890-56781234abcd")
    private UUID idUsuario;

    @Schema(description = "Número de plazas reservadas por el usuario.", example = "3")
    private int plazasReservadas;

    @Schema(description = "Identificador del evento al que pertenece la reserva.", example = "456f7890-c12d-45e6-b678-123456abcdef")
    private UUID idEvento;

    @Schema(description = "Indica si la reserva ha sido cancelada.", example = "false")
    private boolean cancelado;

    @Schema(description = "Fecha y hora de inicio del evento asociado a la reserva.", example = "2023-10-01T14:30:00")
    private LocalDateTime fechaInicioEvento;

    @Schema(description = "Nombre del evento asociado a la reserva.", example = "Concierto de Rock")
    private String nombreEvento;

    public ReservaDto() {
    }

    public ReservaDto(UUID id, UUID idUsuario, int plazasReservadas, UUID idEvento, boolean cancelado, LocalDateTime fechaInicioEvento, String nombreEvento) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.plazasReservadas = plazasReservadas;
        this.idEvento = idEvento;
        this.cancelado = cancelado;
        this.fechaInicioEvento = fechaInicioEvento;
        this.nombreEvento = nombreEvento;
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdUsuario() {
        return idUsuario;
    }

    public int getPlazasReservadas() {
        return plazasReservadas;
    }

    public UUID getIdEvento() {
        return idEvento;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public LocalDateTime getFechaInicioEvento() {
        return fechaInicioEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }
}
