package reservas.infraestructura.repositorios.reservas;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import reservas.dominio.Reserva;

@Repository
public interface RepositorioReservasMongodb
    extends RepositorioReservas, MongoRepository<Reserva, UUID> {

  @Query("{ 'evento.$id': ?0 }")
  Page<Reserva> findAllByEventoId(String eventoId, Pageable pageable);

  @Query(value = "{ 'idUsuario': ?0 }", exists = true)
  boolean existsByIdUsuario(String idUsuario);

  @Query(value = "{ 'idUsuario': ?0 }")
  List<Reserva> findAllByIdUsuario(String idUsuario);
}
