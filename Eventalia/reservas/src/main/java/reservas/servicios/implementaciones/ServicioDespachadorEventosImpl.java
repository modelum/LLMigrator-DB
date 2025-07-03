package reservas.servicios.implementaciones;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reservas.dominio.Evento;
import reservas.infraestructura.repositorios.eventos.RepositorioEventos;
import reservas.infraestructura.repositorios.excepciones.EntidadNoEncontrada;
import reservas.infraestructura.repositorios.reservas.RepositorioReservas;
import reservas.servicios.ServicioDespachadorEventos;

@Service
public class ServicioDespachadorEventosImpl implements ServicioDespachadorEventos {
  private final RepositorioEventos repositorioEventos;
  private final RepositorioReservas repositorioReservas;

  public ServicioDespachadorEventosImpl(
      RepositorioEventos repositorioEventos, RepositorioReservas repositorioReservas) {
    this.repositorioEventos = repositorioEventos;
    this.repositorioReservas = repositorioReservas;
  }

  @Override
  public void despacharCreacionEvento(
      UUID idEvento, int plazasDisponibles, boolean cancelado, LocalDateTime fechaInicio, String nombreEvento) throws Exception {
    this.repositorioEventos.save(new Evento(idEvento, plazasDisponibles, cancelado, fechaInicio, nombreEvento));
  }

  @Override
  public void despacharCancelacionEvento(UUID idEvento) throws Exception {
    Evento evento =
        this.repositorioEventos
            .findById(idEvento)
            .orElseThrow(() -> new EntidadNoEncontrada("Evento no encontrado"));
    evento.cancelar();
    evento
        .getReservas()
        .forEach(
            reserva -> {
              reserva.cancelar();
              this.repositorioReservas.save(reserva);
            });
    evento.setReservas(List.of());
    this.repositorioEventos.save(evento);
  }

  @Override
  public void despacharModificacionEvento(
      UUID idEvento, int plazasMaximasDisponibles, LocalDateTime fechaInicio) throws Exception {
    Evento evento =
        this.repositorioEventos
            .findById(idEvento)
            .orElseThrow(() -> new EntidadNoEncontrada("Evento no encontrado"));
    evento.setPlazasDisponibles(plazasMaximasDisponibles - evento.getPlazasReservadas());
    evento.setFechaInicio(fechaInicio);
    this.repositorioEventos.save(evento);
  }
}
