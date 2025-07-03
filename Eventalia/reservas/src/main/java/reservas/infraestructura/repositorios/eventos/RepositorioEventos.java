package reservas.infraestructura.repositorios.eventos;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reservas.dominio.Evento;

@NoRepositoryBean
public interface RepositorioEventos extends CrudRepository<Evento, UUID> {}
