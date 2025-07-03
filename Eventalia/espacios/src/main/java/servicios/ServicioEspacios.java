package servicios;

import dominio.EspacioFisico;
import dominio.PuntoInteres;
import infraestructura.externalAPIs.rabbitMQ.excepciones.BusEventosException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import infraestructura.repositorio.excepciones.EntidadNoEncontrada;
import infraestructura.repositorio.excepciones.RepositorioException;

public interface ServicioEspacios {

  /**
   * - ruta de acceso: "/espacios" - método: POST - body: parametros de alta de espacio fisico -
   * respuesta: id del espacio fisico creado + 201 Created + location header con la URL del recurso
   * creado
   */
  UUID darAltaEspacioFisico(
      final String nombre,
      final String propietario,
      final int capacidad,
      final String direccionPostal,
      final double longitud,
      final double latitud,
      final String descripcion)
      throws RepositorioException, EntidadNoEncontrada, BusEventosException;

  /**
   * - ruta de acceso: "/espacios/{idEspacio}/puntosinteres" - método: PUT - body: lista de puntos
   * de interes - respuesta: 204 No Content
   */
  boolean asignarPuntosInteres(final UUID idEspacio, Collection<PuntoInteres> puntosInteres)
      throws RepositorioException, EntidadNoEncontrada;

  /**
   * - ruta de acceso: "/espacios/{idEspacio}" - método: PATCH - body: parametros de modificacion de
   * espacio fisico - respuesta: 204 NO_CONTENT (la habitual) || otra opcion 200 ok más el objeto
   * modificado
   */
  EspacioFisico modificarEspacioFisico(
      final UUID idEspacio, final String nombre, final String descripcion, final int capacidad)
      throws RepositorioException, EntidadNoEncontrada, BusEventosException, IOException;

  /**
   * - ruta de acceso: "/espacios/{idEspacio}/estado" - método: PUT - body: enum("activo",
   * "cerrado") - respuesta: 204 NO_CONTENT
   */
  boolean darBajaEspacioFisico(final UUID idEspacio)
      throws RepositorioException, EntidadNoEncontrada, BusEventosException, IOException;

  boolean activarEspacioFisico(final UUID idEspacio)
      throws RepositorioException, EntidadNoEncontrada, BusEventosException;

  /**
   * - ruta de acceso:
   * "/espacios/libres?fechaInicio={fechaInicio}&fechaFin={fechaFin}&capacidadRequerida={capacidadRequerida}"
   * - método: GET - respuesta: 200 OK, BODY:lista de espacios fisicos libres
   *
   * @throws IOException
   */
  List<EspacioFisico> findEspaciosFisicosLibres(
      LocalDateTime fechaInicio, LocalDateTime fechaFin, int capacidadRequerida)
      throws RepositorioException, EntidadNoEncontrada, IOException;

  /**
   * - ruta de acceso: "/espacios?propietario={propietario}" - método: GET - respuesta: 200 OK,
   * BODY:lista de espacios fisicos de un propietario
   */
  List<EspacioFisico> findEspaciosFisicosDePropietario(final String propietario)
      throws RepositorioException, EntidadNoEncontrada;

  /**
   * - ruta de acceso: "/espacios/{idEspacio}" - método: GET - respuesta: 200 OK, BODY:espacio
   * fisico
   */
  EspacioFisico recuperarEspacioFisico(final UUID idEspacio)
      throws RepositorioException, EntidadNoEncontrada;

  List<EspacioFisico> recuperarEspaciosFisicos() throws RepositorioException;
}
