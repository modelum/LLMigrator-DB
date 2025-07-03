package eventos.infraestructura.repositorios.espacios;

import eventos.dominio.EspacioFisico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositorioEspaciosJpa
    extends RepositorioEspacios, JpaRepository<EspacioFisico, UUID> {}
