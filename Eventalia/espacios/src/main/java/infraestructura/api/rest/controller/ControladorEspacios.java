package infraestructura.api.rest.controller;

import infraestructura.api.rest.DTO.CrearEspacioFisicoDTO;
import infraestructura.api.rest.DTO.EspacioFisicoDTO;
import infraestructura.api.rest.DTO.ListaPuntosInteresDTO;
import infraestructura.api.rest.DTO.ModificarEspacioFisicoDTO;
import infraestructura.api.rest.mapper.EspacioFisicoMapper;
import infraestructura.api.rest.utils.Listado;
import infraestructura.api.rest.utils.Listado.ResumenExtendido;
import infraestructura.externalAPIs.rabbitMQ.excepciones.BusEventosException;
import infraestructura.repositorio.excepciones.EntidadNoEncontrada;
import infraestructura.repositorio.excepciones.RepositorioException;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import servicios.ServicioEspacios;
import servicios.factoria.*;

@Path("espacios")
public class ControladorEspacios {

  private ServicioEspacios servicio = FactoriaServicios.getServicio(ServicioEspacios.class);

  @Context private UriInfo uriInfo;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed("PROPIETARIO_ESPACIOS")
  public Response darAltaEspacioFisico(CrearEspacioFisicoDTO espacioFisico)
      throws RepositorioException, EntidadNoEncontrada, BusEventosException {
    UUID id =
        servicio.darAltaEspacioFisico(
            espacioFisico.getNombre(),
            espacioFisico.getPropietario(),
            espacioFisico.getCapacidad(),
            espacioFisico.getDireccionPostal(),
            espacioFisico.getLongitud(),
            espacioFisico.getLatitud(),
            espacioFisico.getDescripcion());

    URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id.toString()).build();

    return Response.created(nuevaURL).entity(id).build();
  }

  @PUT
  @Path("/{id}/puntosinteres")
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed("PROPIETARIO_ESPACIOS")
  public Response asignarPuntosInteres(
      @PathParam("id") UUID id, ListaPuntosInteresDTO listaPuntosInteres)
      throws RepositorioException, EntidadNoEncontrada {

    servicio.asignarPuntosInteres(id, listaPuntosInteres.getPuntos());

    return Response.status(Response.Status.NO_CONTENT).build();
  }

  @PATCH
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @RolesAllowed("PROPIETARIO_ESPACIOS")
  public Response modificarEspacioFisico(
      @PathParam("id") UUID id, ModificarEspacioFisicoDTO espacio)
      throws RepositorioException, EntidadNoEncontrada, BusEventosException, IOException {

    servicio.modificarEspacioFisico(
        id, espacio.getNombre(), espacio.getDescripcion(), espacio.getCapacidad());

    return Response.status(Response.Status.NO_CONTENT).build();
  }

  @PUT
  @Path("/{id}/estado")
  @RolesAllowed("PROPIETARIO_ESPACIOS")
  public Response cambiarEstadoEspacioFisico(
      @PathParam("id") UUID id, @FormParam("estado") String estado)
      throws RepositorioException, EntidadNoEncontrada, BusEventosException, IOException {

    boolean success = false;
    if ("activo".equalsIgnoreCase(estado)) {
      success = servicio.activarEspacioFisico(id);
    } else if ("cerrado".equalsIgnoreCase(estado)) {
      success = servicio.darBajaEspacioFisico(id);
    } else {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity("Estado inválido. Use 'activo' o 'cerrado'.")
          .build();
    }

    return !success
        ? Response.status(Response.Status.CONFLICT).build()
        : Response.status(Response.Status.NO_CONTENT).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/libres")
  @RolesAllowed({"PROPIETARIO_ESPACIOS", "USUARIO"})
  public Response findEspaciosFisicosLibres(
      @QueryParam("fechaInicio") String fechaInicio,
      @QueryParam("fechaFin") String fechaFin,
      @QueryParam("capacidadRequerida") int capacidadRequerida)
      throws RepositorioException, EntidadNoEncontrada, IOException {

    LocalDateTime fechaInicioParse = null, fechaFinParse = null;
    try {
      fechaInicioParse = LocalDateTime.parse(fechaInicio);
      fechaFinParse = LocalDateTime.parse(fechaFin);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException(e);
    }

    List<EspacioFisicoDTO> listaEspacioFisicosLibres =
        servicio
            .findEspaciosFisicosLibres(fechaInicioParse, fechaFinParse, capacidadRequerida)
            .stream()
            .map(EspacioFisicoMapper::transformToEspacioFisicoDTO)
            .collect(Collectors.toList());

    LinkedList<ResumenExtendido> extendido = new LinkedList<>();
    for (EspacioFisicoDTO espacioFisicoDTO : listaEspacioFisicosLibres) {
      ResumenExtendido resumenExtendido = new ResumenExtendido();
      resumenExtendido.setResumen(espacioFisicoDTO);

      // Construir la URL
      UUID id = espacioFisicoDTO.getId();
      URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
      resumenExtendido.setUrl(nuevaURL.toString());

      extendido.add(resumenExtendido);
    }

    Listado listado = new Listado();
    listado.setEspacios(extendido);
    return Response.ok(listado).build();
  }

  @GET
  @Path("/propietarios") // /api/espacios/propietarios?propietario=propietario
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed({"PROPIETARIO_ESPACIOS", "USUARIO"})
  public Response findEspaciosFisicosDePropietario(@QueryParam("propietario") String propietario)
      throws RepositorioException, EntidadNoEncontrada {
    List<EspacioFisicoDTO> listaEspacioFisicosPropietario =
        servicio.findEspaciosFisicosDePropietario(propietario).stream()
            .map(EspacioFisicoMapper::transformToEspacioFisicoDTO)
            .collect(Collectors.toList());

    LinkedList<ResumenExtendido> extendido = new LinkedList<>();
    for (EspacioFisicoDTO espacioFisicoDTO : listaEspacioFisicosPropietario) {
      ResumenExtendido resumenExtendido = new ResumenExtendido();
      resumenExtendido.setResumen(espacioFisicoDTO);

      // Construir la URL
      UUID id = espacioFisicoDTO.getId();
      URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
      resumenExtendido.setUrl(nuevaURL.toString());

      extendido.add(resumenExtendido);
    }

    Listado listado = new Listado();
    listado.setEspacios(extendido);
    return Response.ok(listado).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}")
  @RolesAllowed({"PROPIETARIO_ESPACIOS", "USUARIO"})
  public Response recuperarEspacioFisico(@PathParam("id") UUID id)
      throws RepositorioException, EntidadNoEncontrada {
    EspacioFisicoDTO espacioFisico =
        EspacioFisicoMapper.transformToEspacioFisicoDTO(servicio.recuperarEspacioFisico(id));

    return Response.ok(espacioFisico).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @RolesAllowed("PROPIETARIO_ESPACIOS")
  public Response recuperarEspaciosFisicos() throws RepositorioException {
    List<EspacioFisicoDTO> listaEspacioFisicos =
        servicio.recuperarEspaciosFisicos().stream()
            .map(EspacioFisicoMapper::transformToEspacioFisicoDTO)
            .collect(Collectors.toList());

    LinkedList<ResumenExtendido> extendido = new LinkedList<>();

    listaEspacioFisicos.forEach(
        espacioFisicoDTO -> {
          ResumenExtendido resumenExtendido = new ResumenExtendido();
          resumenExtendido.setResumen(espacioFisicoDTO);

          UUID id = espacioFisicoDTO.getId();
          URI nuevaURL = this.uriInfo.getAbsolutePathBuilder().path(id.toString()).build();
          resumenExtendido.setUrl(nuevaURL.toString());

          extendido.add(resumenExtendido);
        });
    Listado listado = new Listado();
    listado.setEspacios(extendido);
    return Response.ok(listado).build();
  }
}
