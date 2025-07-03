package reservas.servicios;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ServicioDespachadorEventos {
  void despacharCreacionEvento(UUID idEvento, int plazasDisponibles, boolean cancelado, LocalDateTime fechaInicio, String nombreEvento)
      throws Exception;

  void despacharCancelacionEvento(UUID idEvento) throws Exception;

  void despacharModificacionEvento(UUID idEvento, int plazasMaximasDisponibles, LocalDateTime fechaInicio)
      throws Exception;
}
