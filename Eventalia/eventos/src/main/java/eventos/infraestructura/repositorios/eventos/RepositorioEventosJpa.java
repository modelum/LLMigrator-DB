package eventos.infraestructura.repositorios.eventos;

import eventos.dominio.Evento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEventosJpa extends RepositorioEventos, JpaRepository<Evento, UUID> {
  // getEventos del mes
  @Query(
      "SELECT e "
          + "FROM Evento e "
          + "WHERE e.ocupacion IS NOT NULL AND e.cancelado = FALSE "
          + "AND FUNCTION('YEAR', e.ocupacion.fechaInicio) = :anio "
          + "AND FUNCTION('MONTH', e.ocupacion.fechaInicio) = :mes")
  @Override
  public Page<Evento> getEventosPorMesYAnio(
      @Param("mes") int mes, @Param("anio") int anio, Pageable pageable);

  @Query(
      "SELECT CASE WHEN (COUNT(e) > 0) THEN TRUE ELSE FALSE END "
          + "FROM Evento e "
          + "WHERE e.ocupacion.espacioFisico.id = :idEspacio "
          + "AND e.ocupacion.fechaFin > CURRENT_TIMESTAMP")
  @Override
  boolean isOcupacionActiva(@Param("idEspacio") UUID idEspacio);

  @Query(
      "SELECT e.id "
          + "FROM EspacioFisico e "
          + "WHERE e.capacidad >= :capacidadMinima "
          + "AND e.estado = 'ACTIVO' "
          + "AND NOT EXISTS ("
          + "SELECT ev FROM Evento ev "
          + "WHERE ev.ocupacion.espacioFisico.id = e.id "
          + "AND ev.ocupacion.fechaInicio <= :fechaFin "
          + "AND ev.ocupacion.fechaFin >= :fechaInicio)")
  @Override
  List<UUID> getEspaciosSinEventosYCapacidadSuficiente(
      int capacidadMinima, LocalDateTime fechaInicio, LocalDateTime fechaFin);

  @Query(
      "SELECT COUNT(e) FROM Evento e "
          + "WHERE e.ocupacion.espacioFisico.id = :idEspacio "
          + "AND e.ocupacion.fechaFin > CURRENT_TIMESTAMP "
          + "AND e.plazas > :nuevaCapacidad")
  @Override
  Long getEventosConCapacidadMayorQueNuevaCapacidad(
      @Param("idEspacio") UUID idEspacio, @Param("nuevaCapacidad") int nuevaCapacidad);

  @Override
  @Query(
      "SELECT e "
          + "FROM Evento e "
          + "WHERE e.ocupacion.espacioFisico.id = :idEspacio "
          + "AND e.cancelado = FALSE")
  List<Evento> getEventosPorEspacio(UUID idEspacio);

  @Override
  @Query(
      "SELECT CASE WHEN (COUNT(e) > 0) THEN TRUE ELSE FALSE END "
          + "FROM Evento e "
          + "WHERE e.ocupacion.espacioFisico.id = :idEspacio "
          + "AND e.cancelado = FALSE "
          + "AND (e.ocupacion.fechaInicio < :fechaFin AND e.ocupacion.fechaFin > :fechaInicio)")
  boolean existeEventoSolapado(
      @Param("idEspacio") UUID idEspacio,@Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
}
