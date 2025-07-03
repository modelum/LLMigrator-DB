package eventos.dominio;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

@Embeddable
public class Ocupacion {

  @Column(nullable = true)
  private LocalDateTime fechaInicio;

  @Column(nullable = true)
  private LocalDateTime fechaFin;

  @ManyToOne(optional = true)
  private EspacioFisico espacioFisico;

  public Ocupacion() {}

  public Ocupacion(LocalDateTime fechaInicio, LocalDateTime fechaFin, EspacioFisico espacioFisico) {
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.espacioFisico = espacioFisico;
  }

  public boolean isActiva() {
    return fechaFin.isAfter(LocalDateTime.now());
  }

  // Getters y Setters

  public LocalDateTime getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(LocalDateTime fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public LocalDateTime getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(LocalDateTime fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getNombreEspacioFisico() {
    return espacioFisico.getNombre();
  }

  public EspacioFisico getEspacioFisico() {
    return espacioFisico;
  }

  public void setEspacioFisico(EspacioFisico espacioFisico) {
    this.espacioFisico = espacioFisico;
  }

  // HashCode y Equals

  @Override
  public int hashCode() {
    return Objects.hash(espacioFisico, fechaFin, fechaInicio);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Ocupacion other = (Ocupacion) obj;
    return Objects.equals(espacioFisico, other.espacioFisico)
        && Objects.equals(fechaFin, other.fechaFin)
        && Objects.equals(fechaInicio, other.fechaInicio);
  }
}
