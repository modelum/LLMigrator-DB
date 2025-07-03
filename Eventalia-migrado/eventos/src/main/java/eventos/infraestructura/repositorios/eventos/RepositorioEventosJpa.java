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
public interface RepositorioEventosJpa extends JpaRepository<Evento, UUID> {

  @Query(
      """
                SELECT e FROM Evento e
                WHERE e.ocupacion IS NOT NULL AND e.cancelado = FALSE
                AND EXTRACT(YEAR FROM e.ocupacion.fechaInicio) = :anio
                AND EXTRACT(MONTH FROM e.ocupacion.fechaInicio) = :mes
            """)
  Page<Evento> findEventosPorMesYAnio(
      @Param("mes") int mes, @Param("anio") int anio, Pageable pageable);

  @Query(
      """
                SELECT COUNT(e) > 0 FROM Evento e
                WHERE e.ocupacion.espacioFisico.id = :idEspacio
                AND e.ocupacion.fechaFin > CURRENT_TIMESTAMP
            """)
  boolean isOcupacionActiva(@Param("idEspacio") UUID idEspacio);

  @Query(
      """
                SELECT ef.id FROM EspacioFisico ef
                WHERE ef.capacidad >= :capacidadMinima
                AND ef.estado = 'ACTIVO'
                AND NOT EXISTS (
                    SELECT e FROM Evento e
                    WHERE e.ocupacion.espacioFisico.id = ef.id
                    AND e.ocupacion.fechaInicio <= :fechaFin
                    AND e.ocupacion.fechaFin >= :fechaInicio
                )
            """)
  List<UUID> findEspaciosSinEventosYCapacidadSuficiente(
      @Param("capacidadMinima") int capacidadMinima,
      @Param("fechaInicio") LocalDateTime fechaInicio,
      @Param("fechaFin") LocalDateTime fechaFin);

  @Query(
      """
                SELECT COUNT(e) FROM Evento e
                WHERE e.ocupacion.espacioFisico.id = :idEspacio
                AND e.ocupacion.fechaFin > CURRENT_TIMESTAMP
                AND e.plazas > :nuevaCapacidad
            """)
  Long countEventosConCapacidadMayorQueNuevaCapacidad(
      @Param("idEspacio") UUID idEspacio, @Param("nuevaCapacidad") int nuevaCapacidad);

  @Query(
      """
                SELECT e FROM Evento e
                WHERE e.ocupacion.espacioFisico.id = :idEspacio
                AND e.cancelado = FALSE
            """)
  List<Evento> findEventosPorEspacio(@Param("idEspacio") UUID idEspacio);

  @Query(
      """
                SELECT COUNT(e) > 0 FROM Evento e
                WHERE e.ocupacion.espacioFisico.id = :idEspacio
                AND e.cancelado = FALSE
                AND (e.ocupacion.fechaInicio < :fechaFin AND e.ocupacion.fechaFin > :fechaInicio)
            """)
  boolean existeEventoSolapado(
      @Param("idEspacio") UUID idEspacio,
      @Param("fechaInicio") LocalDateTime fechaInicio,
      @Param("fechaFin") LocalDateTime fechaFin);
}
