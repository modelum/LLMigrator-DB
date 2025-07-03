package eventos.infraestructura.api.rest.spec;

import eventos.infraestructura.api.rest.dto.in.CrearEventoDto;
import eventos.infraestructura.api.rest.dto.in.ModificarEventoDTO;
import eventos.infraestructura.api.rest.dto.out.EventoDTO;
import eventos.infraestructura.api.rest.handler.errorDto.ErrorDto;
import eventos.infraestructura.api.rest.mapper.EventoMapper;
import eventos.infraestructura.repositorios.excepciones.EntidadNoEncontrada;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Eventos", description = "Operaciones relacionadas con la gestión de eventos")
public interface EventosApi {

  @Operation(
      operationId = "darAltaEvento",
      summary = "Registrar un nuevo evento",
      description =
          "Crea un nuevo evento con la información proporcionada. Requiere rol `GESTOR_EVENTOS`.",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"eventos"})
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Evento creado correctamente"),
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @PostMapping("/eventos")
  ResponseEntity<Void> darAltaEvento(
      @Valid
          @RequestBody(
              description = "Datos para crear el evento",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CrearEventoDto.class)))
          CrearEventoDto crearEventoDto)
      throws Exception;

  @Operation(
      operationId = "modificarEvento",
      summary = "Modificar un evento existente",
      description = "Actualiza la información de un evento. Requiere rol `GESTOR_EVENTOS`.",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"eventos"})
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Evento modificado correctamente"),
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    @ApiResponse(
        responseCode = "404",
        description = "Evento no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @PatchMapping("/eventos/{id}")
  ResponseEntity<Void> modificarEvento(
      @PathVariable @Parameter(description = "ID del evento a modificar", required = true) UUID id,
      @Valid
          @RequestBody(
              description = "Datos a modificar",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = ModificarEventoDTO.class)))
          ModificarEventoDTO modificarEventoDTO)
      throws Exception;

  @Operation(
      operationId = "cancelarEvento",
      summary = "Cancelar un evento",
      description =
          "Permite cancelar un evento existente mediante su identificador. Requiere rol `GESTOR_EVENTOS`.",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"eventos"})
  @ApiResponses({
    @ApiResponse(responseCode = "204", description = "Evento cancelado correctamente"),
    @ApiResponse(
        responseCode = "404",
        description = "Evento no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @PutMapping("/eventos/{id}")
  ResponseEntity<Void> cancelarEvento(
      @PathVariable @Parameter(description = "ID del evento", required = true) UUID idEvento)
      throws EntidadNoEncontrada;

  @Operation(
      operationId = "getEventosDelMes",
      summary = "Obtener eventos de un mes específico",
      description =
          "Devuelve la lista de eventos programados para un mes y año determinados. Requiere rol `GESTOR_EVENTOS` o `USUARIO`.",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"eventos"})
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista de eventos recuperada",
        content = @Content(mediaType = "application/json")),
    @ApiResponse(
        responseCode = "400",
        description = "Parámetros inválidos",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping("/eventos/fechas")
  PagedModel<EntityModel<EventoDTO>> getEventosDelMes(
      @RequestParam @Parameter(description = "Número del mes en formato MM", required = true)
          String mes,
      @RequestParam @Parameter(description = "Año en formato YYYY", required = true) String anio,
      @ParameterObject Pageable pageable)
      throws Exception;

    @Operation(
        operationId = "getEventos",
        summary = "Obtener todos los eventos",
        description =
            "Devuelve una lista de todos los eventos registrados. Requiere rol `GESTOR_EVENTOS` o `USUARIO`.",
        security = @SecurityRequirement(name = "bearerAuth"),
        tags = {"eventos"})
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de eventos recuperada",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EventoDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
  @GetMapping("/eventos")
  CollectionModel<EntityModel<EventoDTO>> getEventos() ;

  @Operation(
      operationId = "recuperarEvento",
      summary = "Recuperar detalles de un evento",
      description =
          "Devuelve los datos de un evento mediante su ID. Requiere rol `GESTOR_EVENTOS` o `USUARIO`.",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"eventos"})
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Detalles recuperados",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EventoDTO.class))),
    @ApiResponse(
        responseCode = "404",
        description = "Evento no encontrado",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping("/eventos/{id}")
  EntityModel<EventoDTO> recuperarEvento(
      @PathVariable @Parameter(description = "ID del evento", required = true) UUID id)
      throws EntidadNoEncontrada;

  @Operation(
      operationId = "getEspaciosSinEventosYCapacidadSuficiente",
      summary = "Espacios disponibles con capacidad suficiente",
      description =
          "Devuelve los IDs de espacios que cumplen las condiciones dadas. Requiere rol `MICROSERVICIO`.",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"eventos"})
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista de espacios disponible",
        content = @Content(mediaType = "application/json")),
    @ApiResponse(
        responseCode = "400",
        description = "Parámetros inválidos",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping("/eventos/espaciosLibres")
  ResponseEntity<List<UUID>> getEspaciosSinEventosYCapacidadSuficiente(
      @RequestParam @Parameter(description = "Capacidad mínima", required = true) int capacidad,
      @RequestParam
          @Parameter(
              description = "Inicio (ISO 8601)",
              example = "2025-06-01T10:00:00",
              required = true)
          String fechaInicio,
      @RequestParam
          @Parameter(
              description = "Fin (ISO 8601)",
              example = "2025-06-01T12:00:00",
              required = true)
          String fechaFin)
      throws EntidadNoEncontrada;

  @Operation(
      operationId = "isOcupacionActiva",
      summary = "Verificar ocupación activa",
      description = "Indica si un evento tiene ocupación activa. Requiere rol `MICROSERVICIO`.",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"eventos"})
  @GetMapping("/eventos/{id}/ocupacion")
  ResponseEntity<Boolean> isOcupacionActiva(
      @PathVariable @Parameter(description = "ID del evento", required = true) UUID id)
      throws EntidadNoEncontrada;

  @Operation(
      operationId = "validarNuevaCapacidadEspacio",
      summary = "Validar capacidad de espacio físico",
      description =
          "Verifica si la nueva capacidad es válida según los eventos asignados. Requiere rol `MICROSERVICIO`.",
      security = @SecurityRequirement(name = "bearerAuth"),
      tags = {"eventos"})
  @GetMapping("/eventos/ocupaciones/espacios/{idEspacio}/capacidad")
  ResponseEntity<Boolean> validarNuevaCapacidadEspacio(
      @PathVariable @Parameter(description = "ID del espacio físico", required = true)
          UUID idEspacio,
      @RequestParam @Parameter(description = "Nueva capacidad propuesta", required = true)
          int nuevaCapacidad)
      throws EntidadNoEncontrada;
}
