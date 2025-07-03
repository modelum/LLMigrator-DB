package reservas.infraestructura.api.rest.assembler;

import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;
import reservas.infraestructura.api.rest.dto.out.ReservaDto;

@Component
public class PagedReservaDtoAssembler extends PagedResourcesAssembler<ReservaDto> {
    public PagedReservaDtoAssembler(HateoasPageableHandlerMethodArgumentResolver resolver) {
        super(resolver, null);
    }
}
