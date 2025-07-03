package eventos.servicios;

import eventos.dominio.Evento;
import eventos.dominio.enumerados.Categoria;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import eventos.infraestructura.repositorios.excepciones.EntidadNoEncontrada;
import eventos.infraestructura.api.rest.dto.out.EventoDTO;

public interface ServicioEventos {

  /**
   * Da de alta un evento en el sistema. - ruta de acceso: "/eventos" - método: POST - parámetros:
   * nombre, descripción, organizador, categoría, fechaInicio, fechaFin, plazas, idEspacioFisico -
   * respuesta: 201 CREATED, body: idEvento o el evento completo + cabecera location con la URI del
   * evento creado
   */
  UUID darAltaEvento(
      final String nombre,
      final String descripcion,
      final String organizador,
      final Categoria categoria,
      final LocalDateTime fechaInicio,
      final LocalDateTime fechaFin,
      final int plazas,
      final UUID idEspacioFisico)
      throws EntidadNoEncontrada;

  /**
   * - ruta de acceso: "/eventos/{idEvento}" - método: PATCH - parámetros: descripción, fechaInicio,
   * fechaFin, plazas, idEspacioFisico - respuesta: 204 NO CONTENT
   */
  Evento modificarEvento(
      final UUID idEvento,
      final String descripcion,
      final LocalDateTime fechaInicio,
      final LocalDateTime fechaFin,
      final int plazas,
      final UUID idEspacioFisico)
      throws EntidadNoEncontrada;

  /**
   * - ruta de acceso: "/eventos/{idEvento}/ocupacion" - método: PUT - cuerpo: -- - respuesta: 204
   * NO CONTENT
   */
  boolean cancelarEvento(final UUID idEvento) throws EntidadNoEncontrada;

  /**
   * - ruta de acceso: "/eventos?mes={mes}" - método: GET - respuesta: 200 OK, body: listado eventos
   */
  Page<EventoDTO> getEventosDelMes(YearMonth mes, Pageable pageable) throws EntidadNoEncontrada;

  /** - ruta de acceso: "/eventos/{idEvento}" - método: GET - respuesta: 200 OK, body: evento */
  Evento recuperarEvento(final UUID idEvento) throws EntidadNoEncontrada;

  List<Evento> getEventos();

  /**
   * - ruta de acceso: "/eventos/espaciosOcupados?fechaInicio={fechaInicio}&fechaFin={fechaFin}" -
   * método: GET - respuesta: 200 OK
   */
  List<UUID> getEspaciosSinEventosYCapacidadSuficiente(
      int capacidad, final LocalDateTime fechaInicio, final LocalDateTime fechaFin);

  /**
   * - ruta de acceso: "/eventos/{idEvento}/ocupacion" - método: GET - respuesta: 200 OK, body: "si"
   * o "no"
   */
  boolean isOcupacionActiva(final UUID idEspacioFisico) throws EntidadNoEncontrada;

  boolean validarNuevaCapacidadEspacio(final UUID idEspacio, final int nuevaCapacidad) throws EntidadNoEncontrada;
}
