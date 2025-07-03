package reservas.infraestructura.api.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import reservas.infraestructura.api.rest.controller.ControladorReservas;
import reservas.infraestructura.api.rest.dto.out.ReservaDto;

@Component
public class ReservaDtoAssembler
    implements RepresentationModelAssembler<ReservaDto, EntityModel<ReservaDto>> {
  @Override
  public EntityModel<ReservaDto> toModel(ReservaDto reservaDto) {
    try {
      return EntityModel.of(
          reservaDto,
          linkTo(methodOn(ControladorReservas.class).getReserva(reservaDto.getId())).withSelfRel());

    } catch (Exception e) {
      return EntityModel.of(reservaDto);
    }
  }

  @Override
  public CollectionModel<EntityModel<ReservaDto>> toCollectionModel(
      Iterable<? extends ReservaDto> entities) {
    List<EntityModel<ReservaDto>> models =
        StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());
    try {
      return CollectionModel.of(
          models,
          linkTo(
                  methodOn(ControladorReservas.class)
                      .getReservas(
                          Objects.requireNonNull(models.get(0).getContent()).getIdUsuario()))
              .withSelfRel());
    } catch (Exception e) {
      return CollectionModel.of(models);
    }
  }
}
