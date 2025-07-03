package eventos.infraestructura.repositorios.espacios;

import eventos.dominio.EspacioFisico;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

@NoRepositoryBean
public interface RepositorioEspacios
    extends CrudRepository<EspacioFisico, UUID>, PagingAndSortingRepository<EspacioFisico, UUID> {}
