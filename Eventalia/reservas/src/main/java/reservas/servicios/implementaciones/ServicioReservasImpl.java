package reservas.servicios.implementaciones;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reservas.dominio.Evento;
import reservas.dominio.Reserva;
import reservas.infraestructura.rabbitMQ.PublicadorEventos;
import reservas.infraestructura.repositorios.eventos.RepositorioEventos;
import reservas.infraestructura.repositorios.excepciones.EntidadNoEncontrada;
import reservas.infraestructura.repositorios.reservas.RepositorioReservas;
import reservas.servicios.ServicioReservas;

@Service
public class ServicioReservasImpl implements ServicioReservas {

  private final RepositorioReservas repositorioReservas;
  private final RepositorioEventos repositorioEventos;
  private final PublicadorEventos publicadorEventos;

  public ServicioReservasImpl(
      RepositorioReservas repositorioReservas,
      RepositorioEventos repositorioEventos,
      PublicadorEventos publicadorEventos) {
    this.repositorioReservas = repositorioReservas;
    this.repositorioEventos = repositorioEventos;
    this.publicadorEventos = publicadorEventos;
  }

  @Override
  public UUID reservar(UUID idEvento, UUID idUsuario, int plazasReservadas) throws Exception {
    if (idEvento == null || idUsuario == null) {
      throw new IllegalArgumentException(
          "El id del evento y el id del usuario no pueden ser nulos ni estar vacios");
    }

    if (plazasReservadas <= 0) {
      throw new IllegalArgumentException("El número de plazas reservadas debe ser mayor que 0");
    }

    Evento evento = repositorioEventos
        .findById(idEvento)
        .orElseThrow(() -> new EntidadNoEncontrada("Evento no encontrado"));

    if (plazasReservadas > evento.getPlazasDisponibles()) {
      throw new IllegalArgumentException(
          "No hay suficientes plazas disponibles en este evento: " + evento.getPlazasDisponibles());
    }

    Reserva reserva = this.repositorioReservas.save(new Reserva(idUsuario, plazasReservadas, evento));

    evento.add(reserva);
    evento.setPlazasDisponibles(evento.getPlazasDisponibles() - plazasReservadas);

    repositorioEventos.save(evento);

    publicadorEventos.publicarCreacionReserva(reserva);

    return reserva.getId();
  }

  @Override
  public Reserva get(UUID idReserva) throws Exception {
    return repositorioReservas
        .findById(idReserva)
        .orElseThrow(() -> new EntidadNoEncontrada("Reserva no encontrada"));
  }

  @Override
  public Page<Reserva> getAll(UUID idEvento, Pageable pageable) throws Exception {
    if (!repositorioEventos.existsById(idEvento)) {
      throw new EntidadNoEncontrada("Evento no encontrado");
    }
    return repositorioReservas.findAllByEventoId(idEvento.toString(), pageable);
  }

  @Override
  public List<Reserva> getAll(UUID idUsuario) throws Exception {
    if (idUsuario == null) {
      throw new IllegalArgumentException("El id del usuario no puede ser nulo");
    }
    if (!repositorioReservas.existsByIdUsuario(idUsuario.toString())) {
      throw new EntidadNoEncontrada("No se han encontrado reservas para el usuario");
    }

    return repositorioReservas.findAllByIdUsuario(idUsuario.toString());
  }

  @Override
  public void cancelar(UUID idReserva) throws Exception {
    if (idReserva == null) {
      throw new IllegalArgumentException("El id de la reserva no puede ser nulo");
    }
    Reserva reserva = repositorioReservas
        .findById(idReserva)
        .orElseThrow(() -> new EntidadNoEncontrada("Reserva no encontrada"));

    reserva.cancelar();
    repositorioReservas.save(reserva);

    Evento evento = reserva.getEvento();
    evento.setPlazasDisponibles(evento.getPlazasDisponibles() + reserva.getPlazasReservadas());
    repositorioEventos.save(evento);
  }

  @Override
  public boolean validarNuevasPlazasEvento(UUID idEvento, int plazas) {
    if (idEvento == null) {
      throw new IllegalArgumentException("El id del evento no puede ser nulo");
    }
    if (plazas <= 0) {
      throw new IllegalArgumentException("El número de plazas debe ser mayor que 0");
    }
    Evento evento = repositorioEventos
        .findById(idEvento)
        .orElseThrow(() -> new EntidadNoEncontrada("Evento no encontrado"));

    return evento.getPlazasReservadas() <= plazas;
  }
}
