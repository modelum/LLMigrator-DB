package eventos.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class Ocupacion {

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @ManyToOne(optional = true)
    @JoinColumn(name = "espacio_fisico_id")
    private EspacioFisico espacioFisico;

    public Ocupacion() {
    }

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

    public EspacioFisico getEspacioFisico() {
        return espacioFisico;
    }

    public void setEspacioFisico(EspacioFisico espacioFisico) {
        this.espacioFisico = espacioFisico;
    }

    public String getNombreEspacioFisico() {
        return espacioFisico != null ? espacioFisico.getNombre() : null;
    }

    // Equals y HashCode
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Ocupacion other)) return false;
        return Objects.equals(fechaInicio, other.fechaInicio) && Objects.equals(fechaFin, other.fechaFin) && Objects.equals(espacioFisico, other.espacioFisico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, fechaFin, espacioFisico);
    }
}
