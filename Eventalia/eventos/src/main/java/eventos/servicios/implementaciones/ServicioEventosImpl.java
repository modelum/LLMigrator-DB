package eventos.servicios.implementaciones;

import eventos.dominio.EspacioFisico;
import eventos.dominio.Evento;
import eventos.dominio.enumerados.Categoria;
import eventos.dominio.enumerados.EstadoEspacioFisico;
import eventos.infraestructura.api.rest.dto.out.EventoDTO;
import eventos.infraestructura.api.rest.mapper.EventoMapper;
import eventos.infraestructura.externalAPIs.reservas.ReservasAPI;
import eventos.infraestructura.rabbitMQ.PublicadorEventos;
import eventos.infraestructura.repositorios.espacios.RepositorioEspacios;
import eventos.infraestructura.repositorios.eventos.RepositorioEventos;
import eventos.infraestructura.repositorios.excepciones.EntidadNoEncontrada;
import eventos.servicios.ServicioEventos;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServicioEventosImpl implements ServicioEventos {
  private final RepositorioEventos repositorioEventos;
  private final RepositorioEspacios repositorioEspacios;
  private final PublicadorEventos publicadorEventos;
  private final ReservasAPI reservasAPI;

  public ServicioEventosImpl(
      RepositorioEventos repositorioEventos,
      RepositorioEspacios repositorioEspacios,
      PublicadorEventos publicadorEventos,
      ReservasAPI reservasAPI) {
    this.repositorioEventos = repositorioEventos;
    this.repositorioEspacios = repositorioEspacios;
    this.publicadorEventos = publicadorEventos;
    this.reservasAPI = reservasAPI;
  }

  @Override
  public UUID darAltaEvento(
      String nombre,
      String descripcion,
      String organizador,
      Categoria categoria,
      LocalDateTime fechaInicio,
      LocalDateTime fechaFin,
      int plazas,
      UUID idEspacioFisico)
      throws EntidadNoEncontrada {

    if (nombre == null || nombre.isEmpty()) {
      throw new IllegalArgumentException("El nombre del evento no puede ser nulo o vacío.");
    }

    if (descripcion == null || descripcion.isEmpty()) {
      throw new IllegalArgumentException("La descripción del evento no puede ser nula o vacía.");
    }

    if (organizador == null || organizador.isEmpty()) {
      throw new IllegalArgumentException("El organizador del evento no puede ser nulo o vacío.");
    }

    if (categoria == null) {
      throw new IllegalArgumentException("La categoría del evento no puede ser nula.");
    }

    if (fechaInicio == null || fechaFin == null) {
      throw new IllegalArgumentException(
          "Las fechas de inicio y fin del evento no pueden ser nulas.");
    }

    if (fechaInicio.isAfter(fechaFin)) {
      throw new IllegalArgumentException(
          "La fecha de inicio no puede ser posterior a la fecha de fin.");
    }

    if (plazas <= 0) {
      throw new IllegalArgumentException("El número de plazas debe ser mayor a 0.");
    }

    if (idEspacioFisico == null) {
      throw new IllegalArgumentException("El id del espacio físico no puede ser nulo.");
    }

    EspacioFisico espacioFisico =
        repositorioEspacios
            .findById(idEspacioFisico)
            .orElseThrow(() -> new EntidadNoEncontrada("Espacio físico no encontrado"));

    if (repositorioEventos.existeEventoSolapado(espacioFisico.getId(), fechaInicio, fechaFin)) {
      throw new IllegalArgumentException(
          "El evento no puede solaparse con otro evento existente en el mismo espacio físico. Debes elegir otro espacio físico o modificar las fechas del evento.");
    }

    // LA CAPACIDAD TAMBIÉN ES IMPORTANTE
    if (plazas > espacioFisico.getCapacidad()) {
      throw new IllegalArgumentException(
          "El número de plazas no puede ser mayor a la capacidad del espacio físico.");
    }

    // El estado del espacio físico es crucial
    if (espacioFisico.getEstado() != EstadoEspacioFisico.ACTIVO) {
      throw new IllegalArgumentException("El espacio físico no está activo.");
    }

    Evento evento =
        new Evento(
            nombre,
            descripcion,
            organizador,
            plazas,
            fechaInicio,
            fechaFin,
            espacioFisico,
            categoria);

    repositorioEventos.save(evento);

    publicadorEventos.publicarEventoCreacion(evento);

    return evento.getId();
  }

  public Evento modificarEvento(
      UUID idEvento,
      String descripcion,
      LocalDateTime fechaInicio,
      LocalDateTime fechaFin,
      int plazas,
      UUID idEspacioFisico)
      throws EntidadNoEncontrada {
    if (idEvento == null) {
      throw new IllegalArgumentException("El id del evento no puede ser nulo o vacío.");
    }

    if (plazas < 0) {
      throw new IllegalArgumentException("El número de plazas no puede ser negativo ni igual a 0");
    }

    Evento eventoParaModificar =
        repositorioEventos
            .findById(idEvento)
            .orElseThrow(() -> new EntidadNoEncontrada("Evento no encontrado"));

    if (eventoParaModificar.isCancelado()) {
      throw new IllegalArgumentException("No se puede modificar un evento cancelado");
    }

    if (eventoParaModificar.getOcupacion() == null && (fechaInicio != null || fechaFin != null)) {
      throw new IllegalArgumentException(
          "No se puede modificar la fecha de un evento sin ocupación ya que está cancelado");
    }

    if (eventoParaModificar.getOcupacion() == null && idEspacioFisico != null) {
      throw new IllegalArgumentException(
          "No se puede modificar el espacio de un evento sin ocupación ya que está cancelado");
    }

    if (descripcion != null && !descripcion.isEmpty()) {
      eventoParaModificar.setDescripcion(descripcion);
    }

    if (eventoParaModificar.getOcupacion() != null) {
      EspacioFisico espacio = obtenerEspacioDestino(eventoParaModificar, idEspacioFisico);
      int plazasActualizadas = plazas > 0 ? plazas : eventoParaModificar.getPlazas();
      validarCapacidadEspacioFisico(plazasActualizadas, espacio);

      if (plazas > 0) {
        validarNuevasPlazas(plazas, eventoParaModificar);
        eventoParaModificar.setPlazas(plazas);
      }

      if (idEspacioFisico != null) {
        eventoParaModificar.setEspacioFisico(espacio);
      }

      if (fechaInicio != null
          && fechaInicio.isAfter(LocalDateTime.now())
          && eventoParaModificar.getFechaFin().isAfter(fechaInicio)) {
        eventoParaModificar.setFechaInicio(fechaInicio);
      }

      if (fechaFin != null
          && fechaFin.isAfter(LocalDateTime.now())
          && fechaFin.isAfter(eventoParaModificar.getFechaInicio())) {
        eventoParaModificar.setFechaFin(fechaFin);
      }
    }
    repositorioEventos.save(eventoParaModificar);
    publicadorEventos.publicarEventoModificacion(eventoParaModificar);
    return eventoParaModificar;
  }

  private EspacioFisico obtenerEspacioDestino(Evento eventoParaModificar, UUID idEspacioFisico)
      throws EntidadNoEncontrada {
    if (idEspacioFisico == null) {
      return eventoParaModificar.getEspacioFisico();
    }
    EspacioFisico espacioDestino =
        repositorioEspacios
            .findById(idEspacioFisico)
            .orElseThrow(() -> new EntidadNoEncontrada("Espacio físico no encontrado"));
    if (espacioDestino.getEstado() != EstadoEspacioFisico.ACTIVO) {
      throw new IllegalArgumentException("El espacio físico no está activo.");
    }
    return espacioDestino;
  }

  private void validarCapacidadEspacioFisico(int plazas, EspacioFisico espacioFisico) {
    if (espacioFisico != null && plazas > espacioFisico.getCapacidad()) {
      throw new IllegalArgumentException(
          "El número de plazas no puede ser mayor a la capacidad del espacio físico.");
    }
  }

  private void validarNuevasPlazas(int plazas, Evento evento) throws EntidadNoEncontrada {
    if (plazas < evento.getPlazas()) {
      try {
        if (!reservasAPI.validarNuevasPlazasEvento(evento.getId(), plazas)) {
          throw new IllegalArgumentException(
              "No es posible reducir las plazas del evento porque hay más reservas que el nuevo límite propuesto");
        }
      } catch (IOException e) {
        throw new RuntimeException("Error al validar plazas con el sistema de reservas", e);
      }
    }
  }

  @Override
  public boolean cancelarEvento(UUID idEvento) throws EntidadNoEncontrada {
    if (idEvento == null) {
      throw new IllegalArgumentException("El id del evento no puede ser nulo.");
    }

    Evento evento =
        repositorioEventos
            .findById(idEvento)
            .orElseThrow(() -> new EntidadNoEncontrada("Evento no encontrado"));

    evento.setCancelado(true);
    evento.setOcupacion(null);

    repositorioEventos.save(evento);

    // publicamos el evento cancelado
    publicadorEventos.publicarEventoCancelado(evento.getId().toString());
    return true;
  }

  @Override
  public Page<EventoDTO> getEventosDelMes(YearMonth mes, Pageable pageable)
      throws EntidadNoEncontrada {

    if (mes == null) {
      throw new IllegalArgumentException("El mes no puede ser nulo.");
    }

    if (mes.isBefore(YearMonth.now())) {
      throw new IllegalArgumentException("El mes no puede ser anterior al mes actual.");
    }
    Page<Evento> eventosDelMes =
        repositorioEventos.getEventosPorMesYAnio(mes.getMonthValue(), mes.getYear(), pageable);

    if (eventosDelMes.isEmpty()) {
      throw new EntidadNoEncontrada("No se encontraron eventos para el mes especificado");
    }

    return eventosDelMes.map(EventoMapper::toDTO);
  }

  @Override
  public Evento recuperarEvento(UUID idEvento) throws EntidadNoEncontrada {
    return repositorioEventos
        .findById(idEvento)
        .orElseThrow(() -> new EntidadNoEncontrada("Evento no encontrado"));
  }

  @Override
  public List<Evento> getEventos() {
    return StreamSupport.stream(this.repositorioEventos.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public List<UUID> getEspaciosSinEventosYCapacidadSuficiente(
      final int capacidad, final LocalDateTime fechaInicio, final LocalDateTime fechaFin) {

    if (fechaInicio == null || fechaFin == null) {
      throw new IllegalArgumentException("Las fechas de inicio y fin no pueden ser nulas.");
    }

    if (fechaInicio.isAfter(fechaFin)) {
      throw new IllegalArgumentException(
          "La fecha de inicio no puede ser posterior a la fecha de fin.");
    }

    return repositorioEventos.getEspaciosSinEventosYCapacidadSuficiente(
        capacidad, fechaInicio, fechaFin);
  }

  @Override
  public boolean isOcupacionActiva(UUID idEspacioFisico) throws EntidadNoEncontrada {
    if (idEspacioFisico == null) {
      throw new IllegalArgumentException("El id del espacio físico no puede ser nulo o vacío.");
    }
    if (!repositorioEspacios.existsById(idEspacioFisico)) {
      throw new EntidadNoEncontrada("El espacio físico especificado no existe.");
    }

    return repositorioEventos.isOcupacionActiva(idEspacioFisico);
  }

  @Override
  public boolean validarNuevaCapacidadEspacio(UUID idEspacio, int nuevaCapacidad)
      throws EntidadNoEncontrada {
    if (idEspacio == null) {
      throw new IllegalArgumentException("El id del espacio físico no puede ser nulo o vacío.");
    }
    if (nuevaCapacidad < 0) {
      throw new IllegalArgumentException("La nueva capacidad del espacio debe ser mayor que 0.");
    }
    if (!repositorioEspacios.existsById(idEspacio)) {
      throw new EntidadNoEncontrada("El espacio físico especificado no existe.");
    }
    long eventosConCapacidadMayorQueNuevaCapacidad =
        this.repositorioEventos.getEventosConCapacidadMayorQueNuevaCapacidad(
            idEspacio, nuevaCapacidad);
    return eventosConCapacidadMayorQueNuevaCapacidad == 0;
  }
}
