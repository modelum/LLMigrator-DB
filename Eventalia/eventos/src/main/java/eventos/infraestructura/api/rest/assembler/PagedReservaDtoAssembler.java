package eventos.infraestructura.api.rest.assembler;

import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;

import eventos.infraestructura.api.rest.dto.out.EventoDTO;

@Component
public class PagedReservaDtoAssembler extends PagedResourcesAssembler<EventoDTO> {
  public PagedReservaDtoAssembler(HateoasPageableHandlerMethodArgumentResolver resolver) {
    super(resolver, null);
  }
}
