package reservas.infraestructura.repositorios.reservas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import reservas.dominio.Reserva;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepositorioReservas extends JpaRepository<Reserva, UUID> {
    Page<Reserva> findAllByEventoId(UUID eventoId, Pageable pageable);
    boolean existsByIdUsuario(UUID idUsuario);
    List<Reserva> findAllByIdUsuario(UUID idUsuario);
}
