package eventos.infraestructura.repositorios.eventos;

import eventos.dominio.Evento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface RepositorioEventos extends CrudRepository<Evento, UUID>, PagingAndSortingRepository<Evento, UUID> {
  public Page<Evento> getEventosPorMesYAnio(int mes, int anio, Pageable pageable);

  boolean isOcupacionActiva(UUID idEspacio);

  List<UUID> getEspaciosSinEventosYCapacidadSuficiente(
      int capacidadMinima, LocalDateTime fechaFin, LocalDateTime fechaInicio);

  Long getEventosConCapacidadMayorQueNuevaCapacidad(UUID idEspacio,  int nuevaCapacidad);

  List<Evento> getEventosPorEspacio(UUID idEspacio);

  boolean existeEventoSolapado(
      UUID idEspacio, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
