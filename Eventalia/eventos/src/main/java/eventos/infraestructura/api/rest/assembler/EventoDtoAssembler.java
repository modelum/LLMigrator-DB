package eventos.infraestructura.api.rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import eventos.infraestructura.api.rest.controller.ControladorEventos;
import eventos.infraestructura.api.rest.dto.out.EventoDTO;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EventoDtoAssembler
    implements RepresentationModelAssembler<EventoDTO, EntityModel<EventoDTO>> {
  @Override
  public EntityModel<EventoDTO> toModel(EventoDTO eventoDTO) {
    try {
      return EntityModel.of(
          eventoDTO,
          linkTo(methodOn(ControladorEventos.class).recuperarEvento(eventoDTO.getId()))
              .withSelfRel());

    } catch (Exception e) {
      return EntityModel.of(eventoDTO);
    }
  }

  @Override
  public CollectionModel<EntityModel<EventoDTO>> toCollectionModel(
      Iterable<? extends EventoDTO> entities) {
    List<EntityModel<EventoDTO>> models =
        StreamSupport.stream(entities.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());
      return CollectionModel.of(
          models, linkTo(methodOn(ControladorEventos.class).getEventos()).withSelfRel());

  }
}
