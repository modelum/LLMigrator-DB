package reservas.infraestructura.api.rest.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.infraestructura.api.rest.dto.in.CrearReservaDto;
import reservas.infraestructura.api.rest.dto.out.ReservaDto;
import reservas.infraestructura.api.rest.handler.errorDto.ErrorDto;

public interface ReservasApi {

  @Operation(
      operationId = "crearReserva",
      summary = "Crear una reserva dado un evento y un usuario",
      description = "Crea una nueva reserva para un evento determinado.",
      tags = {"reservas"})
  @ApiResponses({
    @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No existe el evento para el que se hace una reserva de plazas",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @PostMapping("/reservas")
  ResponseEntity<Void> crearReserva(
      @RequestBody
          @Parameter(
              description = "Datos de la reserva a crear",
              required = true,
              content = @Content(schema = @Schema(implementation = CrearReservaDto.class)))
          CrearReservaDto crearReservaDto)
      throws Exception;

  @Operation(
      operationId = "getReserva",
      summary = "Obtener una reserva por su ID",
      description = "Devuelve los detalles de una reserva específica.",
      tags = {"reservas"})
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Reserva encontrada",
        content = @Content(schema = @Schema(implementation = ReservaDto.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Solicitud inválida",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    @ApiResponse(
        responseCode = "404",
        description = "Reserva no encontrada",
        content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping("/reservas/{idReserva}")
  EntityModel<ReservaDto> getReserva(
      @PathVariable @Parameter(description = "ID de la reserva a consultar", required = true)
          UUID idReserva)
      throws Exception;

  @Operation(
      operationId = "getReservas",
      summary = "Listar reservas de un evento",
      description = "Obtiene una lista paginada de reservas asociadas a un evento.",
      tags = {"reservas"})
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista de reservas obtenida exitosamente",
        content = @Content(schema = @Schema(implementation = PagedModel.class))),
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
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping("/eventos/{idEvento}/reservas")
  PagedModel<EntityModel<ReservaDto>> getReservas(
      @PathVariable
          @Parameter(
              description = "ID del evento del que se desean obtener reservas",
              required = true)
          UUID idEvento,
      @Parameter(description = "Opciones de paginación") Pageable pageable)
      throws Exception;

  @Operation(
      operationId = "validarNuevasPlazas",
      summary = "Validar nuevas plazas para un evento",
      description =
          "Valida si se pueden añadir nuevas plazas a un evento. Devuelve true si se pueden definir esas plazas, false en caso contrario.",
      tags = {"reservas"})
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Validación exitosa",
        content = @Content(schema = @Schema(implementation = Boolean.class))),
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
        description = "Error interno del servidor",
        content = @Content(schema = @Schema(implementation = ErrorDto.class)))
  })
  @GetMapping("/eventos/{idEvento}/plazas")
  public ResponseEntity<Boolean> validarNuevasPlazas(
      @PathVariable UUID idEvento, @RequestParam int plazas) throws Exception;
}
