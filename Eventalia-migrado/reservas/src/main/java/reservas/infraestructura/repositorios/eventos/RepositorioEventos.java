package reservas.infraestructura.repositorios.eventos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import reservas.dominio.Evento;

@Repository
public interface RepositorioEventos extends JpaRepository<Evento, UUID> {}
