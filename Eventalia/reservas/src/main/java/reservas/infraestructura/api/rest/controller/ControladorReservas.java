package reservas.infraestructura.api.rest.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import reservas.infraestructura.api.rest.assembler.PagedReservaDtoAssembler;
import reservas.infraestructura.api.rest.assembler.ReservaDtoAssembler;
import reservas.infraestructura.api.rest.dto.in.CrearReservaDto;
import reservas.infraestructura.api.rest.dto.out.ReservaDto;
import reservas.infraestructura.api.rest.mapper.ReservaMapper;
import reservas.infraestructura.api.rest.spec.ReservasApi;
import reservas.servicios.ServicioReservas;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ControladorReservas implements ReservasApi {

    private final ServicioReservas servicioReservas;

    private final ReservaDtoAssembler reservaDtoAssembler;

    private final PagedReservaDtoAssembler pagedResourcesAssembler;

    public ControladorReservas(
            ServicioReservas servicioReservas,
            ReservaDtoAssembler reservaDtoAssembler,
            PagedReservaDtoAssembler pagedResourcesAssembler) {
        this.servicioReservas = servicioReservas;
        this.reservaDtoAssembler = reservaDtoAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping("/reservas")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<Void> crearReserva(@Valid @RequestBody CrearReservaDto crearReservaDto)
            throws Exception {
        UUID id = this.servicioReservas.reservar(
                UUID.fromString(crearReservaDto.getIdEvento()),
                UUID.fromString(crearReservaDto.getIdUsuario()),
                crearReservaDto.getPlazasReservadas());
        URI nuevaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(nuevaUri).build();
    }

    @GetMapping("/reservas/{idReserva}")
    @PreAuthorize("hasAuthority('USUARIO')")
    public EntityModel<ReservaDto> getReserva(@PathVariable UUID idReserva) throws Exception {
        return reservaDtoAssembler.toModel(ReservaMapper.toDTO(this.servicioReservas.get(idReserva)));
    }

    @GetMapping("/reservas/usuarios/{idUsuario}")
    @PreAuthorize("hasAuthority('USUARIO')")
    public CollectionModel<EntityModel<ReservaDto>> getReservas(@PathVariable UUID idUsuario)
            throws Exception {
        return reservaDtoAssembler.toCollectionModel(
                this.servicioReservas.getAll(idUsuario).stream()
                        .map(ReservaMapper::toDTO)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/reservas/eventos/{idEvento}")
    @PreAuthorize("hasAnyAuthority('GESTOR_EVENTOS', 'PROPIETARIO_ESPACIOS', 'USUARIO')")
    public PagedModel<EntityModel<ReservaDto>> getReservas(
            @PathVariable UUID idEvento, Pageable pageable) throws Exception {
        return this.pagedResourcesAssembler.toModel(
                this.servicioReservas.getAll(idEvento, pageable).map(ReservaMapper::toDTO),
                reservaDtoAssembler);
    }

    @PatchMapping("/reservas/{idReserva}/cancelacion")
    @PreAuthorize("hasAuthority('USUARIO')")
    public ResponseEntity<Void> cancelarReserva(@PathVariable UUID idReserva) throws Exception {
        this.servicioReservas.cancelar(idReserva);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('MICROSERVICIO')")
    @GetMapping("/eventos/{idEvento}/plazas")
    public ResponseEntity<Boolean> validarNuevasPlazas(
            @PathVariable UUID idEvento, @RequestParam int plazas) throws Exception {
        return ResponseEntity.ok(this.servicioReservas.validarNuevasPlazasEvento(idEvento, plazas));
    }
}
