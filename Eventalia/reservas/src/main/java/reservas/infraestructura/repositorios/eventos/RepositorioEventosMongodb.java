package reservas.infraestructura.repositorios.eventos;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import reservas.dominio.Evento;

@Repository
public interface RepositorioEventosMongodb
    extends RepositorioEventos, MongoRepository<Evento, UUID> {}
